package com.drinksafe.manager.data.repository

import android.util.Log
import com.drinksafe.manager.data.database.BebidaDao
import com.drinksafe.manager.data.database.PerfilDao
import com.drinksafe.manager.data.models.Perfil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await

class PerfilRepository(
    private val perfilDao: PerfilDao,
    private val bebidaDao: BebidaDao // Añadido para actualizar bebidas cuando cambie el perfil
) {

    private val rtdb = FirebaseDatabase.getInstance("https://drinksafe-92e95-default-rtdb.firebaseio.com/")
    private val rtdbRef = rtdb.getReference("configuracion")
    private val firestore = FirebaseFirestore.getInstance()

    val perfil: Flow<Perfil?> = perfilDao.obtenerPerfil()

    /**
     * Intenta recuperar el perfil desde Firestore si no existe localmente.
     */
    suspend fun asegurarPerfilLocal() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val perfilLocal = perfilDao.obtenerPerfil().firstOrNull()
        
        if (perfilLocal == null || perfilLocal.syncCode.isEmpty()) {
            try {
                val doc = firestore.collection("usuarios").document(userId).get().await()
                if (doc.exists()) {
                    val syncCode = doc.getString("syncCode") ?: ""
                    val nombre = doc.getString("nombreUsuario") ?: "Usuario DrinkSafe"
                    val nuevoPerfil = Perfil(nombreUsuario = nombre, syncCode = syncCode)
                    perfilDao.insertarOActualizar(nuevoPerfil)
                    Log.d("PerfilSync", "Perfil recuperado de Firestore: $syncCode")
                }
            } catch (e: Exception) {
                Log.e("PerfilSync", "Error recuperando perfil: ${e.message}")
            }
        }
    }

    /**
     * Escucha el estado de conexión escrito por la Raspberry Pi en Firebase.
     * Verifica que el syncCode coincida con el esperado.
     */
    fun observarEstadoRaspberry(syncCodeEsperado: String): Flow<Boolean> = callbackFlow {
        if (syncCodeEsperado.isEmpty()) {
            trySend(false)
            return@callbackFlow
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Obtenemos los valores de forma más robusta (soportando Boolean, String o Long/Int)
                val conectadoRaw = snapshot.child("conectado").value
                val syncIdActual = snapshot.child("sync_id").value?.toString() ?: ""
                
                val conectado = when (conectadoRaw) {
                    is Boolean -> conectadoRaw
                    is String -> conectadoRaw.lowercase() == "true"
                    is Number -> conectadoRaw.toInt() == 1
                    else -> false
                }
                
                Log.d("ConexionRaspberry", "SyncId Esperado: $syncCodeEsperado, Actual: $syncIdActual, Conectado: $conectado")
                
                // Solo se considera conectado si el flag es true y el ID coincide
                trySend(conectado && syncIdActual.equals(syncCodeEsperado, ignoreCase = true))
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ConexionRaspberry", "Error en listener: ${error.message}")
                close(error.toException())
            }
        }
        rtdbRef.addValueEventListener(listener)
        awaitClose { rtdbRef.removeEventListener(listener) }
    }

    suspend fun actualizarPerfil(perfil: Perfil) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        
        // 1. Guardar perfil localmente
        perfilDao.insertarOActualizar(perfil)
        
        // 2. Actualizar syncCode de todas las bebidas locales del usuario
        bebidaDao.actualizarSyncCodeMasivo(userId, perfil.syncCode)
        
        try {
            // 3. Sincronizar perfil en Realtime Database
            rtdb.getReference("usuarios")
                .child(userId)
                .child("perfil")
                .setValue(perfil)
                .await()

            // 4. Sincronizar perfil en Firestore
            val updates = mapOf(
                "nombreUsuario" to perfil.nombreUsuario,
                "syncCode" to perfil.syncCode
            )
            firestore.collection("usuarios")
                .document(userId)
                .set(updates, SetOptions.merge())
                .await()

            // 5. Actualizar syncCode en todas las bebidas de este usuario en Firebase
            actualizarBebidasEnFirebase(userId, perfil.syncCode)

            // 6. Sincronizar el ID global que lee la Raspberry Pi
            rtdbRef.child("sync_id").setValue(perfil.syncCode).await()
            rtdbRef.child("conectado").setValue(false).await()
            
            Log.d("PerfilSync", "Sincronización masiva completada para $userId con código ${perfil.syncCode}")
        } catch (e: Exception) {
            Log.e("PerfilSync", "Error en sincronización masiva: ${e.message}")
        }
    }

    /**
     * Actualiza el syncCode de todas las bebidas del usuario en RTDB y Firestore.
     */
    private suspend fun actualizarBebidasEnFirebase(userId: String, nuevoSyncCode: String) {
        // Actualizar en RTDB
        val bebidasRtdb = rtdb.getReference("usuarios").child(userId).child("bebidas")
        val snapshot = bebidasRtdb.get().await()
        snapshot.children.forEach { child ->
            child.ref.child("syncCode").setValue(nuevoSyncCode)
        }

        // Actualizar en Firestore
        val bebidasFirestore = firestore.collection("usuarios").document(userId).collection("bebidas")
        val query = bebidasFirestore.get().await()
        if (!query.isEmpty) {
            val batch = firestore.batch()
            query.documents.forEach { doc ->
                batch.update(doc.reference, "syncCode", nuevoSyncCode)
            }
            batch.commit().await()
        }
    }

    fun generarCodigoUnico(): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..4).map { chars.random() }.joinToString("")
    }
}

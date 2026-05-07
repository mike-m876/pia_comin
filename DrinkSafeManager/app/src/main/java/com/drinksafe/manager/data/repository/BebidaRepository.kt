package com.drinksafe.manager.data.repository

import android.util.Log
import com.drinksafe.manager.data.database.BebidaDao
import com.drinksafe.manager.data.models.Bebida
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para la entidad Bebida.
 * Ahora gestiona datos específicos por usuario.
 */
class BebidaRepository(private val bebidaDao: BebidaDao) {

    private val rtdb = FirebaseDatabase.getInstance("https://drinksafe-92e95-default-rtdb.firebaseio.com/")
    private val firestore = FirebaseFirestore.getInstance()

    /** Stream reactivo de todas las bebidas del usuario */
    fun obtenerTodas(userId: String): Flow<List<Bebida>> = bebidaDao.obtenerTodasPorUsuario(userId)

    /** Stream reactivo del conteo total de bebidas del usuario */
    fun contarBebidas(userId: String): Flow<Int> = bebidaDao.contarBebidas(userId)

    /**
     * Inserta una nueva bebida y la sincroniza con Firebase bajo el nodo del usuario.
     */
    suspend fun insertar(bebida: Bebida): Long {
        // 1. Guardar en Room (local)
        val id = bebidaDao.insertar(bebida)
        val bebidaConId = bebida.copy(id = id.toInt())
        
        // 2. Sincronizar con Realtime Database: usuarios/{userId}/bebidas/{id}
        try {
            rtdb.getReference("usuarios")
                .child(bebida.userId)
                .child("bebidas")
                .child(id.toString())
                .setValue(bebidaConId)
                .await()
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error RTDB: ${e.message}")
        }

        // 3. Sincronizar con Cloud Firestore: usuarios/{userId}/bebidas/{id}
        try {
            firestore.collection("usuarios")
                .document(bebida.userId)
                .collection("bebidas")
                .document(id.toString())
                .set(bebidaConId)
                .await()
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error Firestore: ${e.message}")
        }
        
        return id
    }

    suspend fun actualizar(bebida: Bebida) {
        bebidaDao.actualizar(bebida)
        
        rtdb.getReference("usuarios")
            .child(bebida.userId)
            .child("bebidas")
            .child(bebida.id.toString())
            .setValue(bebida).await()

        firestore.collection("usuarios")
            .document(bebida.userId)
            .collection("bebidas")
            .document(bebida.id.toString())
            .set(bebida).await()
    }

    suspend fun eliminar(bebida: Bebida) {
        bebidaDao.eliminar(bebida)
        
        rtdb.getReference("usuarios")
            .child(bebida.userId)
            .child("bebidas")
            .child(bebida.id.toString())
            .removeValue().await()

        firestore.collection("usuarios")
            .document(bebida.userId)
            .collection("bebidas")
            .document(bebida.id.toString())
            .delete().await()
    }

    suspend fun obtenerPorId(id: Int, userId: String): Bebida? {
        return bebidaDao.obtenerPorId(id, userId)
    }

    fun buscar(userId: String, query: String): Flow<List<Bebida>> {
        return bebidaDao.buscar(userId, query)
    }
}

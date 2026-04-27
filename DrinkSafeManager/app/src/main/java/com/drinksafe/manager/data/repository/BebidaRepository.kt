package com.drinksafe.manager.data.repository

import android.util.Log
import com.drinksafe.manager.data.database.BebidaDao
import com.drinksafe.manager.data.models.Bebida
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para la entidad Bebida.
 * Sincroniza datos con Room, Realtime Database y Cloud Firestore.
 */
class BebidaRepository(private val bebidaDao: BebidaDao) {

    // Referencia a Realtime Database
    private val rtdb = FirebaseDatabase.getInstance("https://drinksafe-92e95-default-rtdb.firebaseio.com/")
    private val rtdbRef = rtdb.getReference("bebidas")

    // Referencia a Cloud Firestore
    private val firestore = FirebaseFirestore.getInstance()

    /** Stream reactivo de todas las bebidas */
    val todasLasBebidas: Flow<List<Bebida>> = bebidaDao.obtenerTodas()

    /** Stream reactivo del conteo total de bebidas */
    val totalBebidas: Flow<Int> = bebidaDao.contarBebidas()

    /**
     * Inserta una nueva bebida en Room y la sincroniza con Firebase (RTDB y Firestore).
     */
    suspend fun insertar(bebida: Bebida): Long {
        // 1. Guardar en Room (local)
        val id = bebidaDao.insertar(bebida)
        val bebidaConId = bebida.copy(id = id.toInt())
        
        // 2. Sincronizar con Realtime Database
        try {
            rtdbRef.child(id.toString()).setValue(bebidaConId).await()
            Log.d("FirebaseSync", "Sincronizado con Realtime Database")
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error en RTDB: ${e.message}")
        }

        // 3. Sincronizar con Cloud Firestore
        try {
            firestore.collection("bebidas")
                .document(id.toString())
                .set(bebidaConId)
                .await()
            Log.d("FirebaseSync", "Sincronizado con Firestore")
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error en Firestore: ${e.message}")
        }
        
        return id
    }

    suspend fun actualizar(bebida: Bebida) {
        bebidaDao.actualizar(bebida)
        rtdbRef.child(bebida.id.toString()).setValue(bebida).await()
        firestore.collection("bebidas").document(bebida.id.toString()).set(bebida).await()
    }

    suspend fun eliminar(bebida: Bebida) {
        bebidaDao.eliminar(bebida)
        rtdbRef.child(bebida.id.toString()).removeValue().await()
        firestore.collection("bebidas").document(bebida.id.toString()).delete().await()
    }

    suspend fun obtenerPorId(id: Int): Bebida? {
        return bebidaDao.obtenerPorId(id)
    }

    fun buscar(query: String): Flow<List<Bebida>> {
        return bebidaDao.buscar(query)
    }

    fun obtenerPorTipo(tipo: String): Flow<List<Bebida>> {
        return bebidaDao.obtenerPorTipo(tipo)
    }
}

package com.drinksafe.manager.data.repository

import com.drinksafe.manager.data.database.BebidaDao
import com.drinksafe.manager.data.models.Bebida
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para la entidad Bebida.
 * Actúa como fuente única de verdad entre el ViewModel y la capa de datos.
 * Abstrae el origen de los datos (Room, red, etc.) del resto de la app.
 */
class BebidaRepository(private val bebidaDao: BebidaDao) {

    /** Stream reactivo de todas las bebidas, ordenado por más reciente */
    val todasLasBebidas: Flow<List<Bebida>> = bebidaDao.obtenerTodas()

    /** Stream reactivo del conteo total de bebidas */
    val totalBebidas: Flow<Int> = bebidaDao.contarBebidas()

    /**
     * Inserta una nueva bebida en la base de datos.
     * Debe ejecutarse en un hilo de IO (Dispatchers.IO).
     * @return el ID de la bebida insertada
     */
    suspend fun insertar(bebida: Bebida): Long {
        return bebidaDao.insertar(bebida)
    }

    /**
     * Actualiza los datos de una bebida existente.
     */
    suspend fun actualizar(bebida: Bebida) {
        bebidaDao.actualizar(bebida)
    }

    /**
     * Elimina una bebida de la base de datos.
     */
    suspend fun eliminar(bebida: Bebida) {
        bebidaDao.eliminar(bebida)
    }

    /**
     * Obtiene una bebida por su ID único.
     */
    suspend fun obtenerPorId(id: Int): Bebida? {
        return bebidaDao.obtenerPorId(id)
    }

    /**
     * Busca bebidas por nombre o marca.
     */
    fun buscar(query: String): Flow<List<Bebida>> {
        return bebidaDao.buscar(query)
    }

    /**
     * Filtra bebidas por tipo.
     */
    fun obtenerPorTipo(tipo: String): Flow<List<Bebida>> {
        return bebidaDao.obtenerPorTipo(tipo)
    }
}

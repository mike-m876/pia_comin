package com.drinksafe.manager.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.drinksafe.manager.data.models.Bebida
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para operaciones CRUD sobre la entidad Bebida.
 * Utiliza Flow y LiveData para observación reactiva de cambios.
 */
@Dao
interface BebidaDao {

    /**
     * Inserta una nueva bebida en la base de datos.
     * @return el ID generado automáticamente
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(bebida: Bebida): Long

    /**
     * Actualiza los datos de una bebida existente.
     */
    @Update
    suspend fun actualizar(bebida: Bebida)

    /**
     * Elimina una bebida de la base de datos.
     */
    @Delete
    suspend fun eliminar(bebida: Bebida)

    /**
     * Obtiene todas las bebidas ordenadas por fecha de registro (más reciente primero).
     * Retorna un Flow para observación reactiva desde el ViewModel.
     */
    @Query("SELECT * FROM bebidas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Bebida>>

    /**
     * Obtiene una bebida específica por su ID.
     */
    @Query("SELECT * FROM bebidas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Bebida?

    /**
     * Cuenta el total de bebidas registradas.
     */
    @Query("SELECT COUNT(*) FROM bebidas")
    fun contarBebidas(): Flow<Int>

    /**
     * Busca bebidas por nombre o marca (búsqueda parcial).
     */
    @Query("SELECT * FROM bebidas WHERE nombre LIKE '%' || :query || '%' OR marca LIKE '%' || :query || '%' ORDER BY id DESC")
    fun buscar(query: String): Flow<List<Bebida>>

    /**
     * Obtiene bebidas filtradas por tipo.
     */
    @Query("SELECT * FROM bebidas WHERE tipo = :tipo ORDER BY id DESC")
    fun obtenerPorTipo(tipo: String): Flow<List<Bebida>>
}

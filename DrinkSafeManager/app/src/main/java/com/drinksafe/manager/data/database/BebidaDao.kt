package com.drinksafe.manager.data.database

import androidx.room.*
import com.drinksafe.manager.data.models.Bebida
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para operaciones CRUD sobre la entidad Bebida.
 * Filtrado por userId para multiusuario.
 */
@Dao
interface BebidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(bebida: Bebida): Long

    @Update
    suspend fun actualizar(bebida: Bebida)

    @Delete
    suspend fun eliminar(bebida: Bebida)

    @Query("SELECT * FROM bebidas WHERE userId = :userId ORDER BY id DESC")
    fun obtenerTodasPorUsuario(userId: String): Flow<List<Bebida>>

    @Query("SELECT * FROM bebidas WHERE id = :id AND userId = :userId")
    suspend fun obtenerPorId(id: Int, userId: String): Bebida?

    @Query("SELECT COUNT(*) FROM bebidas WHERE userId = :userId")
    fun contarBebidas(userId: String): Flow<Int>

    @Query("SELECT * FROM bebidas WHERE userId = :userId AND (nombre LIKE '%' || :query || '%' OR marca LIKE '%' || :query || '%') ORDER BY id DESC")
    fun buscar(userId: String, query: String): Flow<List<Bebida>>

    @Query("SELECT * FROM bebidas WHERE userId = :userId AND tipo = :tipo ORDER BY id DESC")
    fun obtenerPorTipo(userId: String, tipo: String): Flow<List<Bebida>>

    @Query("UPDATE bebidas SET syncCode = :nuevoSyncCode WHERE userId = :userId")
    suspend fun actualizarSyncCodeMasivo(userId: String, nuevoSyncCode: String)
}

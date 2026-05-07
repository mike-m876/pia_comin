package com.drinksafe.manager.data.database

import androidx.room.*
import com.drinksafe.manager.data.models.Perfil
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {
    @Query("SELECT * FROM perfil WHERE id = 1")
    fun obtenerPerfil(): Flow<Perfil?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarOActualizar(perfil: Perfil)
}

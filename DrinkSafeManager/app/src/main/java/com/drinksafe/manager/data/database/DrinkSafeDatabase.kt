package com.drinksafe.manager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.drinksafe.manager.data.models.Bebida
import com.drinksafe.manager.data.models.Perfil

/**
 * Clase principal de Room Database para DrinkSafe Manager.
 */
@Database(
    entities = [Bebida::class, Perfil::class],
    version = 5, // Incrementado a 5 para reflejar el nuevo campo syncCode en Bebida
    exportSchema = false
)
abstract class DrinkSafeDatabase : RoomDatabase() {

    abstract fun bebidaDao(): BebidaDao
    abstract fun perfilDao(): PerfilDao

    companion object {
        private const val DATABASE_NAME = "drinksafe_database"

        @Volatile
        private var INSTANCE: DrinkSafeDatabase? = null

        fun getInstance(context: Context): DrinkSafeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkSafeDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

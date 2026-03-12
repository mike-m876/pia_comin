package com.drinksafe.manager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.drinksafe.manager.data.models.Bebida

/**
 * Clase principal de Room Database para DrinkSafe Manager.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
@Database(
    entities = [Bebida::class],
    version = 1,
    exportSchema = false
)
abstract class DrinkSafeDatabase : RoomDatabase() {

    abstract fun bebidaDao(): BebidaDao

    companion object {
        private const val DATABASE_NAME = "drinksafe_database"

        // @Volatile garantiza que los cambios sean visibles en todos los hilos
        @Volatile
        private var INSTANCE: DrinkSafeDatabase? = null

        /**
         * Obtiene la instancia única de la base de datos.
         * Usa double-checked locking para seguridad en multihilo.
         */
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

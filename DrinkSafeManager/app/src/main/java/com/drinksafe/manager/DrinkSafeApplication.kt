package com.drinksafe.manager

import android.app.Application
import com.drinksafe.manager.data.database.DrinkSafeDatabase
import com.drinksafe.manager.data.repository.BebidaRepository

/**
 * Clase Application principal de DrinkSafe Manager.
 * Inicializa los componentes globales de la aplicación:
 * - Base de datos Room
 * - Repositorio de bebidas
 */
class DrinkSafeApplication : Application() {

    /** Instancia de la base de datos, lazy para inicialización diferida */
    val database: DrinkSafeDatabase by lazy {
        DrinkSafeDatabase.getInstance(this)
    }

    /** Repositorio de bebidas disponible globalmente */
    val bebidaRepository: BebidaRepository by lazy {
        BebidaRepository(database.bebidaDao())
    }
}

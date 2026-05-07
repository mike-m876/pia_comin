package com.drinksafe.manager

import android.app.Application
import com.drinksafe.manager.data.database.DrinkSafeDatabase
import com.drinksafe.manager.data.repository.BebidaRepository
import com.drinksafe.manager.data.repository.PerfilRepository

/**
 * Clase Application principal de DrinkSafe Manager.
 * Inicializa los componentes globales de la aplicación.
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

    /** Repositorio de perfil disponible globalmente */
    val perfilRepository: PerfilRepository by lazy {
        PerfilRepository(database.perfilDao(), database.bebidaDao())
    }
}

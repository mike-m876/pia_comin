package com.drinksafe.manager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa el perfil del usuario o la configuración del dispositivo.
 * Contiene el código de sincronización para la Raspberry Pi.
 */
@Entity(tableName = "perfil")
data class Perfil(
    @PrimaryKey val id: Int = 1, // Solo tendremos un perfil localmente
    val nombreUsuario: String = "Usuario DrinkSafe",
    val syncCode: String = "" // El código de 4 caracteres
)

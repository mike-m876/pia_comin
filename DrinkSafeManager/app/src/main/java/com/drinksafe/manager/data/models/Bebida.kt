package com.drinksafe.manager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Entidad Room que representa una bebida registrada en el sistema DrinkSafe.
 * Almacena los datos de referencia capturados durante el análisis inicial.
 */
@Entity(tableName = "bebidas")
data class Bebida(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** Nombre comercial de la bebida */
    val nombre: String,

    /** Marca o fabricante de la bebida */
    val marca: String,

    /** Tipo de bebida: Vodka, Tequila blanco, Otro */
    val tipo: String,

    /** Timestamp de registro en formato legible */
    val fechaRegistro: String = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    ).format(Date()),

    /** Datos espectrales simulados del sensor AS7262 (valores separados por coma) */
    val datosEspectrales: String = "",

    /** Valor de conductividad eléctrica en µS/cm */
    val conductividad: Float = 0f,

    /** Temperatura de la muestra en °C */
    val temperatura: Float = 0f,

    /** Porcentaje de alcohol estimado */
    val alcoholEstimado: Float = 0f,

    /** Notas adicionales del operador */
    val notas: String = ""
)

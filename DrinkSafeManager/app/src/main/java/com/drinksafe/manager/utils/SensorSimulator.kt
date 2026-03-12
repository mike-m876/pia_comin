package com.drinksafe.manager.utils

import kotlin.random.Random

/**
 * Datos sensoriales simulados que retorna la Raspberry Pi.
 * En producción, estos datos vendrían del endpoint HTTP del dispositivo.
 */
data class DatosSensoriales(
    val datosEspectrales: String,  // Valores de 6 canales del AS7262
    val conductividad: Float,       // µS/cm
    val temperatura: Float,         // °C
    val alcoholEstimado: Float      // Porcentaje %
)

/**
 * Simulador de lecturas sensoriales de la Raspberry Pi.
 * Genera datos realistas según el tipo de bebida para propósitos de demostración.
 * En la implementación real, estos datos se obtendrían via HTTP/WebSocket.
 */
object SensorSimulator {

    /**
     * Genera datos sensoriales simulados basados en el tipo de bebida.
     * Los rangos son aproximaciones educativas para fines universitarios.
     */
    fun generarDatosSensoriales(tipoBebida: String): DatosSensoriales {
        return when (tipoBebida) {
            "Vodka" -> generarDatosVodka()
            "Tequila blanco" -> generarDatosTequila()
            else -> generarDatosGenerico()
        }
    }

    /** Vodka: alta pureza, alcohol ~38-40%, conductividad baja */
    private fun generarDatosVodka(): DatosSensoriales {
        val espectral = generarEspectralAS7262(
            violeta = Random.nextFloat() * 200 + 100,
            azul = Random.nextFloat() * 180 + 90,
            verde = Random.nextFloat() * 160 + 80,
            amarillo = Random.nextFloat() * 150 + 70,
            naranja = Random.nextFloat() * 140 + 60,
            rojo = Random.nextFloat() * 130 + 50
        )
        return DatosSensoriales(
            datosEspectrales = espectral,
            conductividad = Random.nextFloat() * 50 + 20,          // 20-70 µS/cm
            temperatura = Random.nextFloat() * 3 + 20,              // 20-23 °C
            alcoholEstimado = Random.nextFloat() * 4 + 37           // 37-41%
        )
    }

    /** Tequila: mayor contenido de congéneres, alcohol ~38-40% */
    private fun generarDatosTequila(): DatosSensoriales {
        val espectral = generarEspectralAS7262(
            violeta = Random.nextFloat() * 220 + 110,
            azul = Random.nextFloat() * 200 + 100,
            verde = Random.nextFloat() * 190 + 95,
            amarillo = Random.nextFloat() * 180 + 90,
            naranja = Random.nextFloat() * 200 + 100,
            rojo = Random.nextFloat() * 210 + 105
        )
        return DatosSensoriales(
            datosEspectrales = espectral,
            conductividad = Random.nextFloat() * 80 + 40,           // 40-120 µS/cm
            temperatura = Random.nextFloat() * 3 + 20,              // 20-23 °C
            alcoholEstimado = Random.nextFloat() * 4 + 36           // 36-40%
        )
    }

    /** Genérico: valores intermedios */
    private fun generarDatosGenerico(): DatosSensoriales {
        val espectral = generarEspectralAS7262(
            violeta = Random.nextFloat() * 300 + 50,
            azul = Random.nextFloat() * 300 + 50,
            verde = Random.nextFloat() * 300 + 50,
            amarillo = Random.nextFloat() * 300 + 50,
            naranja = Random.nextFloat() * 300 + 50,
            rojo = Random.nextFloat() * 300 + 50
        )
        return DatosSensoriales(
            datosEspectrales = espectral,
            conductividad = Random.nextFloat() * 100 + 30,
            temperatura = Random.nextFloat() * 5 + 18,
            alcoholEstimado = Random.nextFloat() * 20 + 25
        )
    }

    /**
     * Formatea los 6 canales espectrales del sensor AS7262.
     * Canales: 450nm, 500nm, 550nm, 570nm, 600nm, 650nm
     */
    private fun generarEspectralAS7262(
        violeta: Float, azul: Float, verde: Float,
        amarillo: Float, naranja: Float, rojo: Float
    ): String {
        return "450nm:${"%.2f".format(violeta)},500nm:${"%.2f".format(azul)}," +
               "550nm:${"%.2f".format(verde)},570nm:${"%.2f".format(amarillo)}," +
               "600nm:${"%.2f".format(naranja)},650nm:${"%.2f".format(rojo)}"
    }
}

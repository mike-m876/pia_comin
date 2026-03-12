package com.drinksafe.manager.viewmodel

import androidx.lifecycle.*
import com.drinksafe.manager.data.models.Bebida
import com.drinksafe.manager.data.repository.BebidaRepository
import com.drinksafe.manager.utils.SensorSimulator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados posibles para el proceso de registro de una bebida.
 */
sealed class RegistroState {
    object Idle : RegistroState()
    object SimulandoSensores : RegistroState()
    data class Guardando(val bebida: Bebida) : RegistroState()
    data class Exitoso(val bebida: Bebida) : RegistroState()
    data class Error(val mensaje: String) : RegistroState()
}

/**
 * Estados de conexión con la Raspberry Pi.
 */
sealed class ConexionState {
    object Verificando : ConexionState()
    object Conectado : ConexionState()
    object Desconectado : ConexionState()
}

/**
 * ViewModel principal para la gestión de bebidas en DrinkSafe Manager.
 * Maneja toda la lógica de negocio entre la UI y el repositorio.
 */
class BebidaViewModel(private val repository: BebidaRepository) : ViewModel() {

    // -------------------------
    // LiveData / StateFlow
    // -------------------------

    /** Lista reactiva de todas las bebidas */
    val todasLasBebidas: LiveData<List<Bebida>> = repository.todasLasBebidas.asLiveData()

    /** Total de bebidas registradas */
    val totalBebidas: LiveData<Int> = repository.totalBebidas.asLiveData()

    /** Estado del proceso de registro */
    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    /** Estado de conexión con la Raspberry Pi */
    private val _conexionState = MutableStateFlow<ConexionState>(ConexionState.Verificando)
    val conexionState: StateFlow<ConexionState> = _conexionState.asStateFlow()

    /** Resultados de búsqueda */
    private val _busquedaQuery = MutableLiveData("")
    val resultadosBusqueda: LiveData<List<Bebida>> = _busquedaQuery.switchMap { query ->
        if (query.isBlank()) repository.todasLasBebidas.asLiveData()
        else repository.buscar(query).asLiveData()
    }

    /** Bebida seleccionada para detalle */
    private val _bebidaSeleccionada = MutableLiveData<Bebida?>()
    val bebidaSeleccionada: LiveData<Bebida?> = _bebidaSeleccionada

    init {
        verificarConexionRaspberry()
    }

    // -------------------------
    // Acciones de registro
    // -------------------------

    /**
     * Registra una nueva bebida con simulación de lectura de sensores.
     * Flujo: simular sensores → generar datos → guardar en DB
     */
    fun registrarBebida(nombre: String, marca: String, tipo: String, notas: String = "") {
        viewModelScope.launch {
            try {
                // Paso 1: Simular lectura de sensores
                _registroState.value = RegistroState.SimulandoSensores
                delay(2500) // Simula tiempo de lectura real de sensores

                // Paso 2: Generar datos simulados del hardware
                val datosSimulados = SensorSimulator.generarDatosSensoriales(tipo)

                // Paso 3: Crear objeto Bebida
                val bebida = Bebida(
                    nombre = nombre.trim(),
                    marca = marca.trim(),
                    tipo = tipo,
                    notas = notas.trim(),
                    datosEspectrales = datosSimulados.datosEspectrales,
                    conductividad = datosSimulados.conductividad,
                    temperatura = datosSimulados.temperatura,
                    alcoholEstimado = datosSimulados.alcoholEstimado
                )

                _registroState.value = RegistroState.Guardando(bebida)
                delay(500) // Pequeña pausa visual

                // Paso 4: Guardar en base de datos
                repository.insertar(bebida)

                _registroState.value = RegistroState.Exitoso(bebida)

            } catch (e: Exception) {
                _registroState.value = RegistroState.Error(
                    "Error al registrar la bebida: ${e.message}"
                )
            }
        }
    }

    /** Reinicia el estado de registro para poder registrar otra bebida */
    fun resetRegistroState() {
        _registroState.value = RegistroState.Idle
    }

    // -------------------------
    // Acciones sobre bebidas
    // -------------------------

    /** Elimina una bebida de la base de datos */
    fun eliminarBebida(bebida: Bebida) {
        viewModelScope.launch {
            repository.eliminar(bebida)
        }
    }

    /** Selecciona una bebida para ver su detalle */
    fun seleccionarBebida(bebida: Bebida) {
        _bebidaSeleccionada.value = bebida
    }

    /** Carga una bebida específica por ID */
    fun cargarBebidaPorId(id: Int) {
        viewModelScope.launch {
            _bebidaSeleccionada.value = repository.obtenerPorId(id)
        }
    }

    // -------------------------
    // Búsqueda
    // -------------------------

    /** Actualiza el query de búsqueda */
    fun buscar(query: String) {
        _busquedaQuery.value = query
    }

    // -------------------------
    // Conexión Raspberry Pi
    // -------------------------

    /**
     * Simula la verificación de conexión con la Raspberry Pi.
     * En una app real, haría una petición HTTP a la IP del dispositivo.
     */
    fun verificarConexionRaspberry() {
        viewModelScope.launch {
            _conexionState.value = ConexionState.Verificando
            delay(1500) // Simula tiempo de ping
            // Simulación: 70% probabilidad de estar conectado
            val conectado = (1..10).random() <= 7
            _conexionState.value = if (conectado) {
                ConexionState.Conectado
            } else {
                ConexionState.Desconectado
            }
        }
    }

    // -------------------------
    // Factory
    // -------------------------

    class Factory(private val repository: BebidaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BebidaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BebidaViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}

package com.drinksafe.manager.viewmodel

import androidx.lifecycle.*
import com.drinksafe.manager.data.models.Bebida
import com.drinksafe.manager.data.repository.BebidaRepository
import com.drinksafe.manager.utils.SensorSimulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

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
 */
class BebidaViewModel(private val repository: BebidaRepository) : ViewModel() {

    // Configuración de la Raspberry Pi (AJUSTAR SEGÚN TU RED)
    private val RASPBERRY_IP = "192.168.1.100" 
    private val RASPBERRY_PORT = 80 // O el puerto que use tu servicio en la Pi

    val todasLasBebidas: LiveData<List<Bebida>> = repository.todasLasBebidas.asLiveData()
    val totalBebidas: LiveData<Int> = repository.totalBebidas.asLiveData()

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    private val _conexionState = MutableStateFlow<ConexionState>(ConexionState.Verificando)
    val conexionState: StateFlow<ConexionState> = _conexionState.asStateFlow()

    private val _busquedaQuery = MutableLiveData("")
    val resultadosBusqueda: LiveData<List<Bebida>> = _busquedaQuery.switchMap { query ->
        if (query.isBlank()) repository.todasLasBebidas.asLiveData()
        else repository.buscar(query).asLiveData()
    }

    private val _bebidaSeleccionada = MutableLiveData<Bebida?>()
    val bebidaSeleccionada: LiveData<Bebida?> = _bebidaSeleccionada

    init {
        verificarConexionRaspberry()
    }

    /**
     * Verifica la conexión real con la Raspberry Pi intentando abrir un socket.
     */
    fun verificarConexionRaspberry() {
        viewModelScope.launch {
            _conexionState.value = ConexionState.Verificando
            
            val estaConectado = withContext(Dispatchers.IO) {
                try {
                    val socket = Socket()
                    // Intenta conectar con un timeout de 2 segundos
                    socket.connect(InetSocketAddress(RASPBERRY_IP, RASPBERRY_PORT), 2000)
                    socket.close()
                    true
                } catch (e: Exception) {
                    false
                }
            }

            _conexionState.value = if (estaConectado) {
                ConexionState.Conectado
            } else {
                ConexionState.Desconectado
            }
        }
    }

    fun registrarBebida(nombre: String, marca: String, tipo: String, notas: String = "") {
        viewModelScope.launch {
            try {
                _registroState.value = RegistroState.SimulandoSensores
                delay(2500) 
                val datosSimulados = SensorSimulator.generarDatosSensoriales(tipo)

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
                delay(500) 
                repository.insertar(bebida)
                _registroState.value = RegistroState.Exitoso(bebida)

            } catch (e: Exception) {
                _registroState.value = RegistroState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetRegistroState() { _registroState.value = RegistroState.Idle }
    fun eliminarBebida(bebida: Bebida) { viewModelScope.launch { repository.eliminar(bebida) } }
    fun seleccionarBebida(bebida: Bebida) { _bebidaSeleccionada.value = bebida }
    fun cargarBebidaPorId(id: Int) { viewModelScope.launch { _bebidaSeleccionada.value = repository.obtenerPorId(id) } }
    fun buscar(query: String) { _busquedaQuery.value = query }

    class Factory(private val repository: BebidaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BebidaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BebidaViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel desconocido")
        }
    }
}

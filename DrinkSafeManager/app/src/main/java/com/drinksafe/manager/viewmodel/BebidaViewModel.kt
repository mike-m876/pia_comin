package com.drinksafe.manager.viewmodel

import androidx.lifecycle.*
import com.drinksafe.manager.data.models.Bebida
import com.drinksafe.manager.data.repository.BebidaRepository
import com.drinksafe.manager.data.repository.PerfilRepository
import com.drinksafe.manager.utils.SensorSimulator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
 */
class BebidaViewModel(
    private val repository: BebidaRepository,
    private val perfilRepository: PerfilRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""

    // -------------------------
    // LiveData / StateFlow
    // -------------------------

    val totalBebidas: LiveData<Int> = repository.contarBebidas(userId).asLiveData()

    private val _busquedaQuery = MutableLiveData("")
    val resultadosBusqueda: LiveData<List<Bebida>> = _busquedaQuery.switchMap { query ->
        if (query.isBlank()) repository.obtenerTodas(userId).asLiveData()
        else repository.buscar(userId, query).asLiveData()
    }

    private val _registroState = MutableStateFlow<RegistroState>(RegistroState.Idle)
    val registroState: StateFlow<RegistroState> = _registroState.asStateFlow()

    private val _conexionState = MutableStateFlow<ConexionState>(ConexionState.Desconectado)
    val conexionState: StateFlow<ConexionState> = _conexionState.asStateFlow()

    private val _bebidaSeleccionada = MutableLiveData<Bebida?>()
    val bebidaSeleccionada: LiveData<Bebida?> = _bebidaSeleccionada

    private val refreshSignal = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    init {
        observarConexionReal()
    }

    /**
     * Escucha el estado de conexión real desde Firebase (vía PerfilRepository).
     * Solo se conecta si el syncCode del perfil coincide con el de la Raspberry.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observarConexionReal() {
        viewModelScope.launch {
            combine(
                perfilRepository.perfil.map { it?.syncCode ?: "" }.distinctUntilChanged(),
                refreshSignal
            ) { syncCode, _ -> syncCode }
                .flatMapLatest { syncCode ->
                    if (syncCode.isEmpty()) flowOf(false)
                    else perfilRepository.observarEstadoRaspberry(syncCode)
                }
                .collectLatest { estaConectado ->
                    _conexionState.value = if (estaConectado) {
                        ConexionState.Conectado
                    } else {
                        ConexionState.Desconectado
                    }
                }
        }
    }

    /**
     * Forzar una verificación manual.
     */
    fun verificarConexionRaspberry() {
        viewModelScope.launch {
            _conexionState.value = ConexionState.Verificando
            delay(800)
            refreshSignal.emit(Unit)
        }
    }

    fun registrarBebida(nombre: String, marca: String, tipo: String, notas: String = "", alcoholManual: Float? = null) {
        if (userId.isEmpty()) return

        viewModelScope.launch {
            try {
                _registroState.value = RegistroState.SimulandoSensores
                delay(2500) 

                // Obtenemos el perfil para recuperar el syncCode actual
                val perfil = perfilRepository.perfil.firstOrNull()
                val syncCode = perfil?.syncCode ?: ""

                val datosSimulados = SensorSimulator.generarDatosSensoriales(tipo)

                val bebida = Bebida(
                    userId = userId,
                    syncCode = syncCode,
                    nombre = nombre.trim(),
                    marca = marca.trim(),
                    tipo = tipo,
                    notas = notas.trim(),
                    datosEspectrales = datosSimulados.datosEspectrales,
                    conductividad = datosSimulados.conductividad,
                    temperatura = datosSimulados.temperatura,
                    alcoholEstimado = alcoholManual ?: datosSimulados.alcoholEstimado
                )

                _registroState.value = RegistroState.Guardando(bebida)
                delay(500) 
                
                repository.insertar(bebida)
                _registroState.value = RegistroState.Exitoso(bebida)

            } catch (e: Exception) {
                _registroState.value = RegistroState.Error("Error al registrar: ${e.message}")
            }
        }
    }

    fun resetRegistroState() { _registroState.value = RegistroState.Idle }
    
    fun eliminarBebida(bebida: Bebida) {
        if (bebida.userId == userId) {
            viewModelScope.launch { repository.eliminar(bebida) }
        }
    }

    fun seleccionarBebida(bebida: Bebida) { _bebidaSeleccionada.value = bebida }

    fun buscar(query: String) {
        _busquedaQuery.value = query
    }

    class Factory(
        private val repository: BebidaRepository,
        private val perfilRepository: PerfilRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BebidaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BebidaViewModel(repository, perfilRepository) as T
            }
            throw IllegalArgumentException("ViewModel desconocido")
        }
    }
}

package com.drinksafe.manager.viewmodel

import androidx.lifecycle.*
import com.drinksafe.manager.data.models.Perfil
import com.drinksafe.manager.data.repository.PerfilRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class PerfilViewModel(private val repository: PerfilRepository) : ViewModel() {

    val perfil: LiveData<Perfil?> = repository.perfil.asLiveData()

    init {
        viewModelScope.launch {
            repository.asegurarPerfilLocal()
        }
    }

    /**
     * Genera un nuevo código de 4 caracteres y lo guarda en el perfil.
     * Sincroniza automáticamente con Firebase.
     */
    fun generarYNuevoCodigoSync() {
        viewModelScope.launch {
            val actual = perfil.value ?: Perfil()
            val nuevoCodigo = repository.generarCodigoUnico()
            repository.actualizarPerfil(actual.copy(syncCode = nuevoCodigo))
        }
    }

    /**
     * Actualiza el nombre de usuario local y remotamente.
     */
    fun actualizarNombreUsuario(nombre: String) {
        viewModelScope.launch {
            val actual = perfil.value ?: Perfil()
            repository.actualizarPerfil(actual.copy(nombreUsuario = nombre))
        }
    }

    /**
     * Cierra la sesión de Firebase.
     */
    fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
    }

    class Factory(private val repository: PerfilRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerfilViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel desconocido")
        }
    }
}

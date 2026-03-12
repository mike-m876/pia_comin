package com.drinksafe.manager.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drinksafe.manager.DrinkSafeApplication
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentRegistrarBebidaBinding
import com.drinksafe.manager.utils.snackbarError
import com.drinksafe.manager.utils.snackbarExito
import com.drinksafe.manager.viewmodel.BebidaViewModel
import com.drinksafe.manager.viewmodel.RegistroState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment para registrar nuevas bebidas en la base de datos DrinkSafe.
 * Simula la lectura de sensores desde la Raspberry Pi durante el registro.
 */
class RegistrarBebidaFragment : Fragment() {

    private var _binding: FragmentRegistrarBebidaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BebidaViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).bebidaRepository
        BebidaViewModel.Factory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarBebidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarUI()
        observarEstadoRegistro()
    }

    private fun configurarUI() {
        // Toolbar back button
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Chips de tipo de bebida
        binding.chipGroupTipo.setOnCheckedStateChangeListener { group, checkedIds ->
            // El tipo se obtiene del chip seleccionado al momento de registrar
        }

        // Botón de registro
        binding.btnRegistrar.setOnClickListener {
            registrarBebida()
        }
    }

    /** Valida el formulario y ejecuta el registro */
    private fun registrarBebida() {
        val nombre = binding.etNombre.text.toString().trim()
        val marca = binding.etMarca.text.toString().trim()
        val notas = binding.etNotas.text.toString().trim()

        // Obtener tipo seleccionado del ChipGroup
        val tipo = when (binding.chipGroupTipo.checkedChipId) {
            R.id.chip_vodka -> "Vodka"
            R.id.chip_tequila -> "Tequila blanco"
            R.id.chip_otro -> "Otro"
            else -> ""
        }

        // Validaciones
        if (nombre.isEmpty()) {
            binding.tilNombre.error = "Ingresa el nombre de la bebida"
            return
        }
        binding.tilNombre.error = null

        if (marca.isEmpty()) {
            binding.tilMarca.error = "Ingresa la marca"
            return
        }
        binding.tilMarca.error = null

        if (tipo.isEmpty()) {
            binding.root.snackbarError("Selecciona el tipo de bebida")
            return
        }

        // Iniciar registro con simulación de sensores
        viewModel.registrarBebida(nombre, marca, tipo, notas)
    }

    /** Observa los cambios de estado del proceso de registro */
    private fun observarEstadoRegistro() {
        lifecycleScope.launch {
            viewModel.registroState.collectLatest { estado ->
                when (estado) {
                    is RegistroState.Idle -> {
                        mostrarFormulario()
                    }
                    is RegistroState.SimulandoSensores -> {
                        mostrarSensoresActivos()
                    }
                    is RegistroState.Guardando -> {
                        mostrarGuardando()
                    }
                    is RegistroState.Exitoso -> {
                        binding.root.snackbarExito("✓ Bebida registrada correctamente")
                        limpiarFormulario()
                        viewModel.resetRegistroState()
                        // Regresar al dashboard después de un momento
                        lifecycleScope.launch {
                            kotlinx.coroutines.delay(1500)
                            if (isAdded) findNavController().navigateUp()
                        }
                    }
                    is RegistroState.Error -> {
                        binding.root.snackbarError(estado.mensaje)
                        mostrarFormulario()
                        viewModel.resetRegistroState()
                    }
                }
            }
        }
    }

    private fun mostrarFormulario() {
        binding.layoutFormulario.visibility = View.VISIBLE
        binding.layoutSensores.visibility = View.GONE
        binding.btnRegistrar.isEnabled = true
        binding.btnRegistrar.text = "Registrar bebida"
    }

    private fun mostrarSensoresActivos() {
        binding.layoutFormulario.visibility = View.GONE
        binding.layoutSensores.visibility = View.VISIBLE
        binding.tvEstadoSensor.text = "Leyendo sensores..."
        binding.tvDescripcionSensor.text = "El sensor AS7262 está analizando la muestra"
        binding.progressSensores.visibility = View.VISIBLE
        binding.btnRegistrar.isEnabled = false
        animarIconoSensor()
    }

    private fun mostrarGuardando() {
        binding.tvEstadoSensor.text = "Guardando en base de datos..."
        binding.tvDescripcionSensor.text = "Sincronizando datos con la Raspberry Pi"
    }

    private fun animarIconoSensor() {
        binding.ivSensorIcon.animate()
            .scaleX(1.2f).scaleY(1.2f)
            .setDuration(500)
            .withEndAction {
                binding.ivSensorIcon.animate()
                    .scaleX(1f).scaleY(1f)
                    .setDuration(500)
                    .withEndAction { if (isAdded) animarIconoSensor() }
                    .start()
            }.start()
    }

    private fun limpiarFormulario() {
        binding.etNombre.text?.clear()
        binding.etMarca.text?.clear()
        binding.etNotas.text?.clear()
        binding.chipGroupTipo.clearCheck()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

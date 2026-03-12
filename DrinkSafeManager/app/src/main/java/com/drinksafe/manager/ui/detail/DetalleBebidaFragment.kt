package com.drinksafe.manager.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.drinksafe.manager.DrinkSafeApplication
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentDetalleBebidaBinding
import com.drinksafe.manager.viewmodel.BebidaViewModel

/**
 * Fragment que muestra el detalle completo de una bebida seleccionada.
 * Incluye datos de los sensores y fecha de registro.
 */
class DetalleBebidaFragment : Fragment() {

    private var _binding: FragmentDetalleBebidaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BebidaViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).bebidaRepository
        BebidaViewModel.Factory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBebidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarToolbar()
        observarBebida()
    }

    private fun configurarToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observarBebida() {
        viewModel.bebidaSeleccionada.observe(viewLifecycleOwner) { bebida ->
            if (bebida == null) {
                findNavController().navigateUp()
                return@observe
            }

            with(binding) {
                // Información básica
                tvNombre.text = bebida.nombre
                tvMarca.text = bebida.marca
                chipTipo.text = bebida.tipo
                tvFechaRegistro.text = "Registrada el ${bebida.fechaRegistro}"

                // Icono según tipo
                val iconRes = when (bebida.tipo) {
                    "Vodka" -> R.drawable.ic_vodka
                    "Tequila blanco" -> R.drawable.ic_tequila
                    else -> R.drawable.ic_drink_generic
                }
                ivIconoBebida.setImageResource(iconRes)

                // Datos sensoriales
                tvAlcoholValor.text = "${"%.2f".format(bebida.alcoholEstimado)}%"
                tvConductividadValor.text = "${"%.1f".format(bebida.conductividad)} µS/cm"
                tvTemperaturaValor.text = "${"%.1f".format(bebida.temperatura)} °C"

                // Datos espectrales del AS7262
                mostrarDatosEspectrales(bebida.datosEspectrales)

                // Notas
                if (bebida.notas.isNotEmpty()) {
                    layoutNotas.visibility = View.VISIBLE
                    tvNotas.text = bebida.notas
                } else {
                    layoutNotas.visibility = View.GONE
                }
            }

            // Animar contenido
            binding.scrollContent.alpha = 0f
            binding.scrollContent.animate().alpha(1f).setDuration(400).start()
        }
    }

    /** Parsea y muestra los datos del sensor espectral AS7262 */
    private fun mostrarDatosEspectrales(datosRaw: String) {
        if (datosRaw.isEmpty()) {
            binding.layoutEspectral.visibility = View.GONE
            return
        }
        binding.layoutEspectral.visibility = View.VISIBLE

        // Parsear formato: "450nm:123.45,500nm:234.56,..."
        val canales = datosRaw.split(",")
        if (canales.size >= 6) {
            binding.tvCanal450.text = canales[0].substringAfter(":")
            binding.tvCanal500.text = canales[1].substringAfter(":")
            binding.tvCanal550.text = canales[2].substringAfter(":")
            binding.tvCanal570.text = canales[3].substringAfter(":")
            binding.tvCanal600.text = canales[4].substringAfter(":")
            binding.tvCanal650.text = canales[5].substringAfter(":")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

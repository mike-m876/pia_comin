package com.drinksafe.manager.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drinksafe.manager.DrinkSafeApplication
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentDashboardBinding
import com.drinksafe.manager.viewmodel.BebidaViewModel
import com.drinksafe.manager.viewmodel.ConexionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Dashboard principal de DrinkSafe Manager.
 * Muestra tarjetas de acceso rápido y el estado de conexión con la Raspberry Pi.
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BebidaViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).bebidaRepository
        BebidaViewModel.Factory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarUI()
        observarDatos()
        animarTarjetas()
    }

    private fun configurarUI() {
        // Tarjeta: Registrar nueva bebida
        binding.cardRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_registrar)
        }

        // Tarjeta: Ver base de datos
        binding.cardBaseDatos.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_listaBebidas)
        }

        // Tarjeta: Configuración (muestra mensaje informativo)
        binding.cardConfiguracion.setOnClickListener {
            mostrarDialogoConfiguracion()
        }

        // Botón refresh de conexión
        binding.btnRefreshConexion.setOnClickListener {
            viewModel.verificarConexionRaspberry()
        }
    }

    private fun observarDatos() {
        // Observar estado de conexión
        lifecycleScope.launch {
            viewModel.conexionState.collectLatest { estado ->
                actualizarEstadoConexion(estado)
            }
        }

        // Observar total de bebidas
        viewModel.totalBebidas.observe(viewLifecycleOwner) { total ->
            binding.tvTotalBebidas.text = "$total bebidas registradas"
        }
    }

    /** Actualiza el indicador visual de conexión con la Raspberry Pi */
    private fun actualizarEstadoConexion(estado: ConexionState) {
        when (estado) {
            is ConexionState.Verificando -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_checking)
                binding.tvEstadoConexion.text = "Verificando conexión..."
                binding.tvEstadoConexion.setTextColor(
                    requireContext().getColor(R.color.azul_tecnologico)
                )
                binding.cardEstadoConexion.strokeColor =
                    requireContext().getColor(R.color.azul_tecnologico)
            }
            is ConexionState.Conectado -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_on)
                binding.tvEstadoConexion.text = "Raspberry Pi conectada"
                binding.tvEstadoConexion.setTextColor(
                    requireContext().getColor(R.color.verde_exito)
                )
                binding.cardEstadoConexion.strokeColor =
                    requireContext().getColor(R.color.verde_exito)
            }
            is ConexionState.Desconectado -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_off)
                binding.tvEstadoConexion.text = "Dispositivo desconectado"
                binding.tvEstadoConexion.setTextColor(
                    requireContext().getColor(R.color.error_color)
                )
                binding.cardEstadoConexion.strokeColor =
                    requireContext().getColor(R.color.error_color)
            }
        }
    }

    /** Animaciones de entrada escalonadas para las tarjetas del dashboard */
    private fun animarTarjetas() {
        val tarjetas = listOf(
            binding.cardEstadoConexion,
            binding.cardRegistrar,
            binding.cardBaseDatos,
            binding.cardConfiguracion
        )
        tarjetas.forEachIndexed { index, card ->
            card.alpha = 0f
            card.animate()
                .alpha(1f)
                .translationYBy(-30f)
                .setStartDelay((index * 120).toLong())
                .setDuration(400)
                .start()
        }
    }

    private fun mostrarDialogoConfiguracion() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Configuración del Sistema")
            .setMessage("DrinkSafe Manager v1.0.0\n\nRaspberry Pi IP: 192.168.1.100\nPuerto: 5000\n\nSensor espectral: AS7262\nSensor conductividad: activo\nSensor temperatura: activo")
            .setPositiveButton("Cerrar", null)
            .setIcon(R.drawable.ic_settings)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

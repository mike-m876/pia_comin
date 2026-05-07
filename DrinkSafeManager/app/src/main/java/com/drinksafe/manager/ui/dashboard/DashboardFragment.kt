package com.drinksafe.manager.ui.dashboard

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
import com.drinksafe.manager.databinding.FragmentDashboardBinding
import com.drinksafe.manager.viewmodel.BebidaViewModel
import com.drinksafe.manager.viewmodel.ConexionState
import com.drinksafe.manager.viewmodel.PerfilViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val bebidaViewModel: BebidaViewModel by activityViewModels {
        val app = (requireActivity().application as DrinkSafeApplication)
        BebidaViewModel.Factory(app.bebidaRepository, app.perfilRepository)
    }

    private val perfilViewModel: PerfilViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).perfilRepository
        PerfilViewModel.Factory(repo)
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
    }

    private fun configurarUI() {
        binding.btnGenerarId.setOnClickListener {
            perfilViewModel.generarYNuevoCodigoSync()
        }

        binding.cardRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_registrar)
        }

        binding.cardBaseDatos.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_listaBebidas)
        }

        binding.btnRefreshConexion.setOnClickListener {
            bebidaViewModel.verificarConexionRaspberry()
        }

        binding.cardConfiguracion.setOnClickListener {
            mostrarDialogoCerrarSesion()
        }
    }

    private fun observarDatos() {
        perfilViewModel.perfil.observe(viewLifecycleOwner) { perfil ->
            perfil?.let {
                binding.tvNombreUsuario.text = it.nombreUsuario
                binding.tvIdSync.text = if (it.syncCode.isEmpty()) "ID Sync: ----" else "ID Sync: ${it.syncCode}"
            }
        }

        lifecycleScope.launch {
            bebidaViewModel.conexionState.collectLatest { estado ->
                actualizarEstadoConexion(estado)
            }
        }

        bebidaViewModel.totalBebidas.observe(viewLifecycleOwner) { total ->
            binding.tvTotalBebidas.text = "$total bebidas registradas"
        }
    }

    private fun actualizarEstadoConexion(estado: ConexionState) {
        when (estado) {
            is ConexionState.Verificando -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_checking)
                binding.tvDispositivoNombre.text = "Raspberry Pi 4"
                binding.tvEstadoConexion.text = "Verificando..."
                binding.tvEstadoConexion.setTextColor(requireContext().getColor(R.color.azul_tecnologico))
            }
            is ConexionState.Conectado -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_on)
                binding.tvDispositivoNombre.text = "Conectada"
                binding.tvEstadoConexion.text = "Sistema listo para análisis"
                binding.tvEstadoConexion.setTextColor(requireContext().getColor(R.color.verde_exito))
            }
            is ConexionState.Desconectado -> {
                binding.ivEstadoConexion.setImageResource(R.drawable.ic_connection_off)
                binding.tvDispositivoNombre.text = "No conectada"
                binding.tvEstadoConexion.text = "Dispositivo fuera de línea"
                binding.tvEstadoConexion.setTextColor(requireContext().getColor(R.color.error_color))
            }
        }
    }

    private fun mostrarDialogoCerrarSesion() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que deseas salir?")
            .setPositiveButton("Salir") { _, _ ->
                perfilViewModel.cerrarSesion()
                findNavController().navigate(R.id.action_splash_to_login)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

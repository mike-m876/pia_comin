package com.drinksafe.manager.ui.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.drinksafe.manager.DrinkSafeApplication
import com.drinksafe.manager.R
import com.drinksafe.manager.databinding.FragmentListaBebidasBinding
import com.drinksafe.manager.viewmodel.BebidaViewModel

/**
 * Fragment que muestra la lista completa de bebidas registradas usando RecyclerView.
 * Incluye búsqueda en tiempo real y filtro por tipo.
 */
class ListaBebidasFragment : Fragment() {

    private var _binding: FragmentListaBebidasBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BebidaViewModel by activityViewModels {
        val repo = (requireActivity().application as DrinkSafeApplication).bebidaRepository
        BebidaViewModel.Factory(repo)
    }

    private lateinit var adapter: BebidaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBebidasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarToolbar()
        configurarRecyclerView()
        configurarBusqueda()
        observarBebidas()
    }

    private fun configurarToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Fab para ir a registrar nueva bebida
        binding.fabAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_listaBebidas_to_registrar)
        }
    }

    private fun configurarRecyclerView() {
        adapter = BebidaAdapter(
            onItemClick = { bebida ->
                viewModel.seleccionarBebida(bebida)
                findNavController().navigate(R.id.action_listaBebidas_to_detalleBebida)
            },
            onDeleteClick = { bebida ->
                mostrarDialogoEliminar(bebida)
            }
        )
        binding.recyclerBebidas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@ListaBebidasFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun configurarBusqueda() {
        binding.etBuscar.doAfterTextChanged { text ->
            viewModel.buscar(text.toString())
        }
    }

    private fun observarBebidas() {
        viewModel.resultadosBusqueda.observe(viewLifecycleOwner) { bebidas ->
            adapter.submitList(bebidas)

            // Mostrar estado vacío
            if (bebidas.isEmpty()) {
                binding.layoutVacio.visibility = View.VISIBLE
                binding.recyclerBebidas.visibility = View.GONE
            } else {
                binding.layoutVacio.visibility = View.GONE
                binding.recyclerBebidas.visibility = View.VISIBLE
            }

            // Actualizar contador
            binding.tvContador.text = "${bebidas.size} bebida${if (bebidas.size != 1) "s" else ""}"
        }
    }

    private fun mostrarDialogoEliminar(bebida: com.drinksafe.manager.data.models.Bebida) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar bebida")
            .setMessage("¿Deseas eliminar '${bebida.nombre}' de la base de datos?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.eliminarBebida(bebida)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

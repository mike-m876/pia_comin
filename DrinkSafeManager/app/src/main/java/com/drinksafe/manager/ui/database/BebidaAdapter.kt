package com.drinksafe.manager.ui.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drinksafe.manager.R
import com.drinksafe.manager.data.models.Bebida
import com.drinksafe.manager.databinding.ItemBebidaBinding

/**
 * Adapter para RecyclerView que muestra las bebidas registradas.
 * Usa ListAdapter con DiffUtil para actualizaciones eficientes.
 */
class BebidaAdapter(
    private val onItemClick: (Bebida) -> Unit,
    private val onDeleteClick: (Bebida) -> Unit
) : ListAdapter<Bebida, BebidaAdapter.BebidaViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaViewHolder {
        val binding = ItemBebidaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BebidaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BebidaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BebidaViewHolder(
        private val binding: ItemBebidaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bebida: Bebida) {
            with(binding) {
                tvNombreBebida.text = bebida.nombre
                tvMarcaBebida.text = bebida.marca
                tvFechaRegistro.text = bebida.fechaRegistro

                // Icono según tipo de bebida
                val iconRes = when (bebida.tipo) {
                    "Vodka" -> R.drawable.ic_vodka
                    "Tequila blanco" -> R.drawable.ic_tequila
                    else -> R.drawable.ic_drink_generic
                }
                ivTipoBebida.setImageResource(iconRes)

                // Color del chip según tipo
                chipTipo.setChipBackgroundColorResource(
                    when (bebida.tipo) {
                        "Vodka" -> R.color.chip_vodka
                        "Tequila blanco" -> R.color.chip_tequila
                        else -> R.color.chip_otro
                    }
                )
                chipTipo.text = bebida.tipo

                // Datos del sensor
                tvAlcohol.text = "${"%.1f".format(bebida.alcoholEstimado)}%"
                tvTemp.text = "${"%.1f".format(bebida.temperatura)}°C"

                // Listeners
                root.setOnClickListener { onItemClick(bebida) }
                btnEliminar.setOnClickListener { onDeleteClick(bebida) }

                // Animación de entrada
                root.alpha = 0f
                root.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Bebida>() {
        override fun areItemsTheSame(oldItem: Bebida, newItem: Bebida): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Bebida, newItem: Bebida): Boolean =
            oldItem == newItem
    }
}

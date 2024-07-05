package com.angiedev.sheystore.ui.modules.seller.products.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemColor2Binding

class AddColorAdapter(
    private val onColorSelected: (String) -> Unit
): RecyclerView.Adapter<AddColorAdapter.AddColorsViewHolder>() {

    val colors = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddColorsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_color_2, parent, false)
    )

    override fun getItemCount() = colors.size

    override fun onBindViewHolder(holder: AddColorsViewHolder, position: Int) {
        holder.render(colors[position])
    }

    fun submitList(colors: List<String>) {
        this.colors.clear()
        this.colors.addAll(colors)
        notifyDataSetChanged()
    }

    fun addColor(color: String) {
        if (!colors.contains(color)) {
            colors.add(color)
            notifyItemInserted(colors.size - 1)
        }
    }

    inner class AddColorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemColor2Binding.bind(view)

        fun render(color: String) {
            binding.root.setCardBackgroundColor(Color.parseColor(color))
            binding.root.setOnClickListener {
                onColorSelected(color)
            }
        }

    }
}
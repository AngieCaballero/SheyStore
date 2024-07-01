package com.angiedev.sheystore.ui.modules.seller.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemColor2Binding

class AddColorAdapter(
    private val colors: List<String>,
    private val onColorSelected: (String) -> Unit
): RecyclerView.Adapter<AddColorAdapter.AddColorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddColorsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_color_2, parent, false)
    )

    override fun getItemCount() = colors.size

    override fun onBindViewHolder(holder: AddColorsViewHolder, position: Int) {
        holder.render(colors[position])
    }

    inner class AddColorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemColor2Binding.bind(view)

        fun render(color: String) {
            binding.root.setOnClickListener {
                //notifyItemChanged()
                onColorSelected(color)
            }
        }

    }
}
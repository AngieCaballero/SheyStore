package com.angiedev.sheystore.ui.modules.buyer.product.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemColorBinding
import com.angiedev.sheystore.ui.utils.extension.parseColor

class ProductColorsAdapter(
    private val colors: List<String>,
    private val onColorSelected: (String) -> Unit
) : RecyclerView.Adapter<ProductColorsAdapter.ProductColorsViewHolder>() {

    private var positionSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductColorsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
    )

    override fun getItemCount() = colors.size

    override fun onBindViewHolder(holder: ProductColorsViewHolder, position: Int) {
        holder.render(colors[position])
    }

    inner class ProductColorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemColorBinding.bind(view)

        fun render(color: String) {
            val gradientColor = GradientDrawable().apply {
                setColor(color.parseColor())
                setStroke(1, binding.root.context.getColor(R.color.white))
                cornerRadii = floatArrayOf(90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f)
            }
            binding.itemProductColor.background = gradientColor

            binding.itemProductCheck.visibility = if (positionSelected == adapterPosition) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            binding.root.setOnClickListener {
                isAnyChecked(positionSelected)
                positionSelected = adapterPosition
                notifyItemChanged(positionSelected)
                onColorSelected(color)
            }
        }

        private fun isAnyChecked(position: Int) {
            notifyItemChanged(position)
        }
    }
}
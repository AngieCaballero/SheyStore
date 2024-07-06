package com.angiedev.sheystore.ui.modules.seller.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.capitalize
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemTableRowBinding
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.ui.utils.helper.getFormattedDate

class ProductSoldQuantityAdapter : RecyclerView.Adapter<ProductSoldQuantityAdapter.ProductSoldQuantityViewHolder>() {

    private val items = mutableListOf<ProductSoldQuantityEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ProductSoldQuantityViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_table_row, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductSoldQuantityViewHolder, position: Int) {
        holder.render(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<ProductSoldQuantityEntity>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ProductSoldQuantityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTableRowBinding.bind(view)

        fun render(item: ProductSoldQuantityEntity) {
            with(binding) {
                itemTableRowDayOfWeek.text = item.date.first.getFormattedDate("EEEE").replaceFirstChar(Char::titlecase)
                itemTableRowCount.text = item.totalQuantity.first.toString()
            }
        }
    }
}
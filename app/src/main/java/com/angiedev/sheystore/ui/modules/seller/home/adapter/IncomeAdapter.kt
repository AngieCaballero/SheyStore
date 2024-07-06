package com.angiedev.sheystore.ui.modules.seller.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemTableRowBinding
import com.angiedev.sheystore.domain.entities.report.income.IncomeEntity
import com.angiedev.sheystore.ui.utils.helper.getFormattedDate

class IncomeAdapter : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    private val items = mutableListOf<IncomeEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = IncomeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_table_row, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.render(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<IncomeEntity>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class IncomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTableRowBinding.bind(view)

        fun render(item: IncomeEntity) {
            with(binding) {
                itemTableRowDayOfWeek.text = item.date.getFormattedDate("EEEE").replaceFirstChar(
                    Char::titlecase)
                itemTableRowCount.text = root.context.resources.getString(R.string.total_price, item.totalPrice.toString())
            }
        }
    }
}
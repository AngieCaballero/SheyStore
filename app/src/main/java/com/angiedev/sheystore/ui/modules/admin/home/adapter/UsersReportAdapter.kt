package com.angiedev.sheystore.ui.modules.admin.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemTableRowBinding
import com.angiedev.sheystore.domain.entities.report.userCount.UserCountEntity
import com.angiedev.sheystore.ui.utils.helper.getFormattedDate

class UsersReportAdapter : RecyclerView.Adapter<UsersReportAdapter.UsersReportViewHolder>() {

    private val items = mutableListOf<UserCountEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<UserCountEntity>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class UsersReportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTableRowBinding.bind(view)

        fun render(item: UserCountEntity) {
            binding.itemTableRowDayOfWeek.text = item.date.getFormattedDate("EEEE").replaceFirstChar(Char::titlecase)
            binding.itemTableRowCount.text = item.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersReportViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_table_row, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UsersReportViewHolder, position: Int) {
        holder.render(items[position])
    }
}
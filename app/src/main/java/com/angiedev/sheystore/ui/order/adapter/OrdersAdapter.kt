package com.angiedev.sheystore.ui.order.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemCartBinding
import com.angiedev.sheystore.databinding.ItemDateBinding
import com.angiedev.sheystore.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.domain.entities.order.OrderEntity
import com.angiedev.sheystore.ui.utils.extension.parseColor
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.helper.getFormattedDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class OrdersAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val orderList: MutableList<OrderViewType> = mutableListOf()

    sealed class OrderViewType {
        data class ItemHeader(val title: String) : OrderViewType()
        data class ItemOrder(val order: CartItemEntity) : OrderViewType()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> holder.bind((orderList[position] as OrderViewType.ItemHeader).title)
            is OrdersViewHolder -> holder.bind((orderList[position] as OrderViewType.ItemOrder).order)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<OrderEntity>) {
        orderList.clear()
        newList.forEach { orderEntity ->
            orderList.add(OrderViewType.ItemHeader(orderEntity.createdAt.getFormattedDate("dd/MM/yyyy")))
            orderEntity.cart.cartItems.forEach {
                orderList.add(OrderViewType.ItemOrder(it))
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = orderList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.item_date -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
                HeaderViewHolder(view)
            }
            R.layout.item_cart -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
                OrdersViewHolder(view)
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(orderList[position]) {
            is OrderViewType.ItemHeader -> R.layout.item_date
            is OrderViewType.ItemOrder -> R.layout.item_cart
        }
    }

    inner class OrdersViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemCartBinding.bind(itemView)

        fun bind(order: CartItemEntity) {
            with(binding) {
                itemCartName.text = order.product.name
                itemCartQuantityStepper.setGone()
                itemCartTotalPrice.text = root.context.resources.getString(R.string.total_price, order.totalPrice.toString())
                val gradientColor = GradientDrawable().apply {
                    setColor(order.color.parseColor())
                    setStroke(1, root.context.getColor(R.color.white))
                    cornerRadii = floatArrayOf(90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f)
                }
                itemCartColor.background = gradientColor
                Glide.with(root.context)
                    .load(order.product.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.product_hint)
                    .into(itemCartImage)



            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemDateBinding.bind(itemView)

        fun bind(title: String) {
            binding.itemDateText.text = title
        }
    }
}
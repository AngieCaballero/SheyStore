package com.angiedev.sheystore.ui.checkout.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.databinding.ItemCartBinding
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setInvisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {

    private val orderList = mutableListOf<CartEntity>()

    inner class OrderListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCartBinding.bind(view)

        fun render(cartItem: CartEntity) {
            with(binding) {
                binding.itemCartRemoveIcon.setInvisible()
                itemCartName.text = cartItem.name
                itemCartQuantityStepper.value = cartItem.quantity.toInt()
                itemCartQuantityStepper.setControlsVisibility(View.GONE)
                itemCartTotalPrice.text = root.context.resources.getString(R.string.total_price, cartItem.totalPrice)
                val gradientColor = GradientDrawable().apply {
                    setColor(Color.parseColor(cartItem.color))
                    setStroke(1, root.context.getColor(R.color.white))
                    cornerRadii = floatArrayOf(90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f)
                }
                itemCartColor.background = gradientColor
                Glide.with(root.context)
                    .load(cartItem.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.product_hint)
                    .into(itemCartImage)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CartEntity>) {
        orderList.clear()
        orderList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
    )

    override fun getItemCount() = orderList.size

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.render(orderList[position])
    }
}
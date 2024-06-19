package com.angiedev.sheystore.ui.modules.buyer.cart.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.databinding.ItemCartBinding
import com.angiedev.sheystore.ui.modules.buyer.product.QuantityStepperListener
import com.angiedev.sheystore.ui.utils.extension.parseColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CartAdapter (
    private val cartItemListener: CartItemListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val listCartItem = mutableListOf<CartItemEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
    )
    override fun getItemCount() = listCartItem.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.render(listCartItem[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CartItemEntity>) {
        listCartItem.clear()
        listCartItem.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position >= listCartItem.size) return
        listCartItem.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCartBinding.bind(view)

        fun render(cartItem: CartItemEntity) {
            with(binding) {
                itemCartName.text = cartItem.product.name
                itemCartQuantityStepper.value = cartItem.quantity
                itemCartTotalPrice.text = root.context.resources.getString(R.string.total_price, cartItem.totalPrice.toString())
                val gradientColor = GradientDrawable().apply {
                    setColor(cartItem.color.parseColor())
                    setStroke(1, root.context.getColor(R.color.white))
                    cornerRadii = floatArrayOf(90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f)
                }
                itemCartColor.background = gradientColor
                Glide.with(root.context)
                    .load(cartItem.product.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.product_hint)
                    .into(itemCartImage)


                // Listeners
                itemCartQuantityStepper.setQuantityStepperListener(object :
                    QuantityStepperListener {
                    override fun onValueChanged(value: Int) {
                        cartItemListener.onValueChangeQuantityStepper(value, cartItem)
                    }
                })

                itemCartRemoveIcon.setOnClickListener {
                    cartItemListener.onRemoveItem(cartItem, adapterPosition)
                }
            }


        }
    }
}
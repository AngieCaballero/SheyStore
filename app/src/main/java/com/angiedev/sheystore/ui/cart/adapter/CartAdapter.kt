package com.angiedev.sheystore.ui.cart.adapter

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
import com.angiedev.sheystore.ui.product.QuantityStepperListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CartAdapter (
    private val cartItemListener: CartItemListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val listCartItem = mutableListOf<CartEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
    )
    override fun getItemCount() = listCartItem.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.render(listCartItem[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CartEntity>) {
        listCartItem.clear()
        listCartItem.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCartBinding.bind(view)

        fun render(cartItem: CartEntity) {
            with(binding) {
                itemCartName.text = cartItem.name
                itemCartQuantityStepper.value = cartItem.quantity.toInt()
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


                // Listeners
                itemCartQuantityStepper.setQuantityStepperListener(object :
                    QuantityStepperListener {
                    override fun onValueChanged(value: Int) {
                        cartItemListener.onValueChangeQuantityStepper(value)
                    }
                })

                itemCartRemoveIcon.setOnClickListener {
                    cartItemListener.onRemoveItem(cartItem, adapterPosition)
                }
            }


        }
    }
}
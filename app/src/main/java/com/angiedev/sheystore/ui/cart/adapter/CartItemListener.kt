package com.angiedev.sheystore.ui.cart.adapter

import com.angiedev.sheystore.data.model.domain.CartItem

interface CartItemListener {
    fun onRemoveItem(cartItem: CartItem)

    fun onValueChangeQuantityStepper(value: Int)
}
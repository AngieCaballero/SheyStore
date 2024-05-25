package com.angiedev.sheystore.ui.cart.adapter

import com.angiedev.sheystore.data.entities.CartEntity

interface CartItemListener {
    fun onRemoveItem(cartItem: CartEntity, position: Int)

    fun onValueChangeQuantityStepper(value: Int)
}
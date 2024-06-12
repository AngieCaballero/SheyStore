package com.angiedev.sheystore.ui.cart.adapter

import com.angiedev.sheystore.domain.entities.cart.CartItemEntity

interface CartItemListener {
    fun onRemoveItem(cartItem: CartItemEntity, position: Int)

    fun onValueChangeQuantityStepper(value: Int, cartItem: CartItemEntity)
}
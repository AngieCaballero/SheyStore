package com.angiedev.sheystore.ui.cart.adapter

import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.model.domain.CartItem

interface CartItemListener {
    fun onRemoveItem(cartItem: CartEntity)

    fun onValueChangeQuantityStepper(value: Int)
}
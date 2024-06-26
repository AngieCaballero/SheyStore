package com.angiedev.sheystore.ui.modules.buyer.order.adapter

import com.angiedev.sheystore.domain.entities.cart.CartItemEntity

interface OrderItemListener {
    fun onItemLeaveReview(orderItem: CartItemEntity)
}
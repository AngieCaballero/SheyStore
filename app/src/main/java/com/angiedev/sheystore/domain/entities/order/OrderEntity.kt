package com.angiedev.sheystore.domain.entities.order

import com.angiedev.sheystore.data.model.remote.response.dto.order.OrderDTO
import com.angiedev.sheystore.domain.entities.cart.CartEntity
import java.util.Date

data class OrderEntity(
    val id: Int,
    val status: String,
    val cart: CartEntity,
    val createdAt: Date
) {
    constructor(orderDTO: OrderDTO?) : this(
        id = orderDTO?.id ?: 0,
        status = orderDTO?.status.orEmpty(),
        cart = CartEntity(orderDTO?.cart),
        createdAt = orderDTO?.createdAt ?: Date()
    )
}
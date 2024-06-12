package com.angiedev.sheystore.domain.entities.cart

import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartDTO

data class CartEntity(
    val id: Int,
    val userId: Int,
    val cartItems: List<CartItemEntity>
) {
    constructor(cartDTO: CartDTO?) : this(
        id = cartDTO?.id ?: 0,
        userId = cartDTO?.userId ?: 0,
        cartItems = cartDTO?.cartItems?.map { CartItemEntity(it) }.orEmpty()
    )
}

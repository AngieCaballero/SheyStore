package com.angiedev.sheystore.data.model.domain

import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity

data class CartItem (
    val id: String,
    val name: String,
    val totalPrice: String,
    val image: String,
    val color: String,
    val quantity: Int
) {
    constructor(productEntity: ProductEntity?, totalPrice: String, quantity: Int) : this (
        id = productEntity?.id.toString(),
        name = productEntity?.name.orEmpty(),
        totalPrice = totalPrice,
        image = productEntity?.image.orEmpty(),
        color = "#8E8E8E",
        quantity = quantity
    )
}
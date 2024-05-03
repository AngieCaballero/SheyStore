package com.angiedev.sheystore.data.model.domain

import com.angiedev.sheystore.data.entities.ProductDetailsEntity

data class CartItem (
    val id: String,
    val name: String,
    val totalPrice: String,
    val image: String,
    val color: String,
    val quantity: Int
) {
    constructor(productDetailsEntity: ProductDetailsEntity?, totalPrice: String, quantity: Int) : this (
        id = productDetailsEntity?.documentId.orEmpty(),
        name = productDetailsEntity?.name.orEmpty(),
        totalPrice = totalPrice,
        image = productDetailsEntity?.images?.firstOrNull().orEmpty(),
        color = "#8E8E8E",
        quantity = quantity
    )
}
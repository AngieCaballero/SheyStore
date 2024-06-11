package com.angiedev.sheystore.data.model.remote.request.cart

import com.google.gson.annotations.SerializedName

data class CreateCartItemDTO(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("color") val color: String,
    @SerializedName("total_price") val totalPrice: Double
)

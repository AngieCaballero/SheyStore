package com.angiedev.sheystore.data.model.remote.response.dto.cart

import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductDTO
import com.google.gson.annotations.SerializedName

data class CartItemDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("total_price") val totalPrice: Double?,
    @SerializedName("product_id") val productId: Int?,
    @SerializedName("color") val color: String?,
    @SerializedName("product") val product: ProductDTO?
)

package com.angiedev.sheystore.data.model.remote.response.dto.cart

import com.google.gson.annotations.SerializedName

data class CartDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("cartItems") val cartItems: List<CartItemDTO>?
)

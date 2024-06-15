package com.angiedev.sheystore.data.model.remote.response.dto.order

import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartDTO
import com.google.gson.annotations.SerializedName
import java.util.Date

data class OrderDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("cart") val cart: CartDTO?,
    @SerializedName("created_at") val createdAt: Date?
)

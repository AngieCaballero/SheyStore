package com.angiedev.sheystore.data.model.remote.request.order

import com.google.gson.annotations.SerializedName

data class CreateOrderDTO(
    @SerializedName("orderStatus") val orderStatus: String,
    @SerializedName("cart_id") val cartId: Int
)
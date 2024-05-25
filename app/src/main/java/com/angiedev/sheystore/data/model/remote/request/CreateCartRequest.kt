package com.angiedev.sheystore.data.model.remote.request

import com.angiedev.sheystore.data.model.remote.response.StringResponse

data class CreateCartRequest(
    val id: StringResponse,
    val color: StringResponse,
    val image: StringResponse,
    val name: StringResponse,
    val quantity: StringResponse,
    val totalPrice: StringResponse
)

package com.angiedev.sheystore.data.model.remote.response.dto.cart

data class CartResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: CartDTO?
)

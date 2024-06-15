package com.angiedev.sheystore.data.model.remote.response.dto.order

data class OrderResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: OrderDTO?
)

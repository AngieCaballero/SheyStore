package com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod

data class PaymentMethodResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: PaymentMethodDTO?
)

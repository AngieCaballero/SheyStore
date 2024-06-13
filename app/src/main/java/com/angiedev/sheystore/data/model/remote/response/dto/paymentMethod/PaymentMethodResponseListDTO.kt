package com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod

import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodDTO

data class PaymentMethodResponseListDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<PaymentMethodDTO>?
)

package com.angiedev.sheystore.domain.entities.paymentMethod

import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodDTO

data class PaymentMethodEntity(
    val id: Int,
    val cardName: String,
    val cardNumber: String,
    val expiredAt: String,
    val cvc: Int,
    var selected: Boolean = false
) {
    constructor(paymentMethodDTO: PaymentMethodDTO?) : this(
        id = paymentMethodDTO?.id ?: 0,
        cardName = paymentMethodDTO?.cardName.orEmpty(),
        cardNumber = paymentMethodDTO?.cardNumber.orEmpty(),
        expiredAt = paymentMethodDTO?.expiredAt.orEmpty(),
        cvc = paymentMethodDTO?.cvcNumber ?: 0
    )
}

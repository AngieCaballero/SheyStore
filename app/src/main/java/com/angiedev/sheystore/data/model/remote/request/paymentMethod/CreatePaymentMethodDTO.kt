package com.angiedev.sheystore.data.model.remote.request.paymentMethod

import com.google.gson.annotations.SerializedName

data class CreatePaymentMethodDTO(
    @SerializedName("card_name") val cardName: String,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("expired_at") val expiredAt: String,
    @SerializedName("cvc_number") val cvcNumber: Int
)

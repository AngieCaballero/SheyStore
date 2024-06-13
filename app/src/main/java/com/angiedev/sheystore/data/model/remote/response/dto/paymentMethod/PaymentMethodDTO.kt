package com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod

import com.google.gson.annotations.SerializedName

data class PaymentMethodDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("card_name") val cardName: String?,
    @SerializedName("card_number") val cardNumber: String?,
    @SerializedName("expired_at") val expiredAt: String?,
    @SerializedName("cvc_number") val cvcNumber: Int?
)

package com.angiedev.sheystore.data.model.remote.response.dto.specialsOffers

import com.google.gson.annotations.SerializedName

data class SpecialOfferDTO(
    @SerializedName("id") val id: Double?,
    @SerializedName("percent_discount") val percentDiscount: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("title") val title: String?
)

package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class SpecialsOffersResponse (
    @SerializedName("id") val id: IntegerResponse? = null,
    @SerializedName("percent_discount") val percentDiscount: StringResponse? = null,
    @SerializedName("description") val description: StringResponse? = null,
    @SerializedName("image") val image: StringResponse? = null,
    @SerializedName("title") val title: StringResponse? = null
)
package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class SpecialsOffersResponse (
    @SerializedName("id") val id: IntegerResponse?,
    @SerializedName("percent_discount") val percentDiscount: StringResponse?,
    @SerializedName("description") val description: StringResponse?,
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("title") val title: StringResponse?
)
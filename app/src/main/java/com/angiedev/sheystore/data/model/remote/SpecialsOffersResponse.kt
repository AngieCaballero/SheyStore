package com.angiedev.sheystore.data.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SpecialsOffersResponse (
    @SerializedName("id") val id: IntegerResponse?,
    @SerializedName("percent_discount") val percentDiscount: StringResponse?,
    @SerializedName("description") val description: StringResponse?,
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("title") val title: StringResponse?
) : Parcelable
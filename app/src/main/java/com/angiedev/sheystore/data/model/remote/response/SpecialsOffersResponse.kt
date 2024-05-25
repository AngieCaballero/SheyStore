package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecialsOffersResponse (
    @SerializedName("id") val id: IntegerResponse?,
    @SerializedName("percent_discount") val percentDiscount: StringResponse?,
    @SerializedName("description") val description: StringResponse?,
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("title") val title: StringResponse?
) : Parcelable
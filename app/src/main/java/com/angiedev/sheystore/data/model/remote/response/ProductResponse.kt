package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductResponse(
    @SerializedName("id") val id: StringResponse?,
    @SerializedName("name")
    val name: StringResponse?,
    @SerializedName("image")
    val image: StringResponse?,
    @SerializedName("price")
    val price: DoubleResponse?,
    @SerializedName("discount")
    val discount: IntegerResponse?,
    @SerializedName("category")
    val category: StringResponse?,
    @SerializedName("rate")
    val rate: StringResponse?
) : Parcelable

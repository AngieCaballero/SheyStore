package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductDetailsResponse(
    @SerializedName("id") val id: StringResponse?,
    @SerializedName("name") val name: StringResponse?,
    @SerializedName("images") val images: ArrayResponse<StringResponse>?,
    @SerializedName("category") val category: StringResponse?,
    @SerializedName("rating") val rating: StringResponse?,
    @SerializedName("price") val price: StringResponse?,
    @SerializedName("description") val description: StringResponse?
) : Parcelable
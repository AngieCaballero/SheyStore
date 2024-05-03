package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("name") val name: StringResponse?
) : Parcelable
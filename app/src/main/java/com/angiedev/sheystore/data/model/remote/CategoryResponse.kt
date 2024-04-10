package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("name") val name: StringResponse?
)
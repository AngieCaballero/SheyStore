package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class ProductResponse(
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
)

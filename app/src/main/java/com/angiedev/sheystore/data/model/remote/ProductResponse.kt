package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("name")
    val name: StringResponse = StringResponse(""),
    @SerializedName("image")
    val image: StringResponse = StringResponse(""),
    @SerializedName("price")
    val price: DoubleResponse = DoubleResponse(0.0),
    @SerializedName("discount")
    val discount: IntegerResponse = IntegerResponse("")
)

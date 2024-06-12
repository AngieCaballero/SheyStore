package com.angiedev.sheystore.data.model.remote.request.shippingAddress

import com.google.gson.annotations.SerializedName

data class UpdateOrCreateShippingAddressDTO(
    @SerializedName("name") val name: String,
    @SerializedName("details") val details: String,
    @SerializedName("default") val default: Boolean
)

package com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress

import com.google.gson.annotations.SerializedName

data class ShippingAddressDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("details") val details: String?,
    @SerializedName("default") val default: Boolean?
)

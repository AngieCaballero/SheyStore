package com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress

data class ShippingAddressResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<ShippingAddressDTO>?
)

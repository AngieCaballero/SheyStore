package com.angiedev.sheystore.data.model.remote.response.dto.product

data class SingleProductResponseDTO (
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: ProductDTO?
)
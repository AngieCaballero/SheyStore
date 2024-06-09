package com.angiedev.sheystore.data.model.remote.response.dto.product

data class ProductResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<ProductDTO>?
)

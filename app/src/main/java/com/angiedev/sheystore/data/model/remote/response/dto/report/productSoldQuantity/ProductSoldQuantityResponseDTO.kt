package com.angiedev.sheystore.data.model.remote.response.dto.report.productSoldQuantity

data class ProductSoldQuantityResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<ProductSoldQuantityDTO>?
)

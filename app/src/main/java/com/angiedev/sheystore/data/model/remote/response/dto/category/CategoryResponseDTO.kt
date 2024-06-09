package com.angiedev.sheystore.data.model.remote.response.dto.category

data class CategoryResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<CategoryDTO>?
)

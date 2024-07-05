package com.angiedev.sheystore.data.model.remote.response.dto.report.topCategories

data class TopCategoriesResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<TopCategoriesDTO>?
)

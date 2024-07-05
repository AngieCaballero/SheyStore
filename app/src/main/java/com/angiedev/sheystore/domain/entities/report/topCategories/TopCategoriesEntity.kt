package com.angiedev.sheystore.domain.entities.report.topCategories

import com.angiedev.sheystore.data.model.remote.response.dto.report.topCategories.TopCategoriesDTO

data class TopCategoriesEntity(
    val category: Pair<String, Int>,
    val totalQuantity: Int
) {
    constructor(topCategoriesDTO: TopCategoriesDTO, index: Int) : this(
        category = Pair(topCategoriesDTO.category.orEmpty(), index),
        totalQuantity = topCategoriesDTO.totalQuantity ?: 0
    )
}

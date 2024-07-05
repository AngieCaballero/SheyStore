package com.angiedev.sheystore.data.repository.report

import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity

interface IReportRepository {
    suspend fun getTopCategories(userId: Int): ApiResponse<List<TopCategoriesEntity>>
}
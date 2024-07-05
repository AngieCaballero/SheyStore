package com.angiedev.sheystore.data.repository.report

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import javax.inject.Inject

class ReportRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : IReportRepository {

    override suspend fun getTopCategories(userId: Int): ApiResponse<List<TopCategoriesEntity>> {
        val response = apiDataSource.getTopCategories(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.mapIndexed { index, topCategoriesDTO ->
                TopCategoriesEntity(topCategoriesDTO, index)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
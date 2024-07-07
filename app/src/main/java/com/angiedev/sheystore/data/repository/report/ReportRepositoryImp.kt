package com.angiedev.sheystore.data.repository.report

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.datasource.remote.ApiDownload
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.report.income.IncomeEntity
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import com.angiedev.sheystore.domain.entities.report.userCount.UserCountEntity
import javax.inject.Inject

class ReportRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val apiDownload: ApiDownload
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

    override suspend fun getIncome(userId: Int): ApiResponse<List<IncomeEntity>> {
        val response = apiDataSource.getIncome(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                IncomeEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getProductSoldQuantity(userId: Int): ApiResponse<List<ProductSoldQuantityEntity>> {
        val response = apiDataSource.getProductSoldQuantity(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                ProductSoldQuantityEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getProductsSoldGlobalQuantity(): ApiResponse<List<ProductSoldQuantityEntity>> {
       val response = apiDataSource.getProductsSoldGlobalQuantity()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.mapIndexed { index, value ->
                ProductSoldQuantityEntity(value, index)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getUsersReport(): ApiResponse<List<UserCountEntity>> {
        val response = apiDataSource.getUserCount()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                UserCountEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun downloadTopCategoriesReport(userId: Int) = apiDownload.downloadTopCategoriesReport(userId)


    override suspend fun downloadProductsSoldQuantityReport(userId: Int) = apiDownload.downloadProductsSoldQuantityReport(userId)

    override suspend fun downloadIncomeReport(userId: Int) = apiDownload.downloadIncomeReport(userId)
}
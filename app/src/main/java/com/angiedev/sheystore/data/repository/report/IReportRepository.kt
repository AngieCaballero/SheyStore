package com.angiedev.sheystore.data.repository.report

import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.report.income.IncomeEntity
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity

interface IReportRepository {

    suspend fun getTopCategories(userId: Int): ApiResponse<List<TopCategoriesEntity>>

    suspend fun getIncome(userId: Int): ApiResponse<List<IncomeEntity>>

    suspend fun getProductSoldQuantity(userId: Int): ApiResponse<List<ProductSoldQuantityEntity>>
}
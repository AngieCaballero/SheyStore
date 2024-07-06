package com.angiedev.sheystore.data.repository.report

import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.report.income.IncomeEntity
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import okhttp3.ResponseBody

interface IReportRepository {

    suspend fun getTopCategories(userId: Int): ApiResponse<List<TopCategoriesEntity>>

    suspend fun getIncome(userId: Int): ApiResponse<List<IncomeEntity>>

    suspend fun getProductSoldQuantity(userId: Int): ApiResponse<List<ProductSoldQuantityEntity>>

    suspend fun getProductsSoldGlobalQuantity(): ApiResponse<List<ProductSoldQuantityEntity>>

    suspend fun downloadTopCategoriesReport(userId: Int): ResponseBody?

    suspend fun downloadProductsSoldQuantityReport(userId: Int): ResponseBody?

    suspend fun downloadIncomeReport(userId: Int): ResponseBody?

}
package com.angiedev.sheystore.data.datasource.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDownload {

    @GET("report/download-top-categories-report/user/{userId}")
    suspend fun downloadTopCategoriesReport(@Path("userId") userId: Int): ResponseBody

    @GET("report/download-products-sold-quantity-report/user/{userId}")
    suspend fun downloadProductsSoldQuantityReport(@Path("userId") userId: Int): ResponseBody

    @GET("report/download-income-report/user/{userId}")
    suspend fun downloadIncomeReport(@Path("userId") userId: Int): ResponseBody

    @GET("backup")
    suspend fun downloadBackup(): ResponseBody
}
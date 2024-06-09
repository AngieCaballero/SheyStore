package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.DocumentResponse
import com.angiedev.sheystore.data.model.remote.response.ProductDetailsResponse
import com.angiedev.sheystore.data.util.parseArray
import com.google.gson.Gson
import javax.inject.Inject

class ProductRepositoryResponseImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : IProductRepository {

    override suspend fun getProducts(): ApiResponse<List<ProductEntity>> {
        val response = apiDataSource.getProducts()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                ProductEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getProductDetails(productId: Int): ApiResponse<ProductDetailsEntity> {
        val response = apiDataSource.getProductDetails(productId)
        return try {
            if (response.isSuccess) {
                val data = response.getOrNull()
                val productDetailsResponse = parseArray<DocumentResponse<ProductDetailsResponse>>(Gson().toJson(data))
                ApiResponse.Success(data = ProductDetailsEntity(productDetailsResponse.fields, data?.name))
            } else {
                ApiResponse.Error(response.exceptionOrNull())
            }
        } catch (e: Exception) {
            ApiResponse.Error(Throwable(e.message))
        }
    }
}
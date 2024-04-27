package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.data.model.remote.DocumentResponse
import com.angiedev.sheystore.data.model.remote.ProductDetailsResponse
import com.angiedev.sheystore.data.util.parseArray
import com.google.gson.Gson
import javax.inject.Inject

class ProductRepositoryResponseImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : IProductRepository {

    override suspend fun getProducts(): ApiResponse<List<ProductEntity>> {
        val response = apiDataSource.getProducts()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.documents?.map {
                ProductEntity(parseArray(Gson().toJson(it.fields)))
            }
            ApiResponse.Success(data.orEmpty())
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getProductDetails(productId: String): ApiResponse<ProductDetailsEntity> {
        val response = apiDataSource.getProductDetails(productId)
        return try {
            if (response.isSuccess) {
                val data = response.getOrNull()
                val jsonToParse = Gson().toJson(data)
                val productDetailsResponse = parseArray<DocumentResponse<ProductDetailsResponse>>(jsonToParse)
                ApiResponse.Success(ProductDetailsEntity(productDetailsResponse.fields, data?.name))
            } else {
                ApiResponse.Error(response.exceptionOrNull())
            }
        } catch (e: Exception) {
            ApiResponse.Error(Throwable(e.message))
        }
    }
}
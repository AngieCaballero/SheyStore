package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.DocumentResponse
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

}
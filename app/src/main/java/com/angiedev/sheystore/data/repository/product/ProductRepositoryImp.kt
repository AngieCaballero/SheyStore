package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
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

    override suspend fun saveProduct(userId: Int, product: ProductEntity): ApiResponse<Boolean> {
        val response = apiDataSource.saveProduct(userId, product)

        return if (response.isSuccess) {
            ApiResponse.Success(data = response.isSuccess)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getProductsBySeller(userId: Int): ApiResponse<List<ProductEntity>> {
        val response = apiDataSource.getProducts(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                ProductEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun updateProduct(product: ProductEntity): ApiResponse<Boolean> {
        val response = apiDataSource.updateProduct(product)

        return if (response.isSuccess) {
            ApiResponse.Success(data = response.isSuccess)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun deleteProduct(productId: Int): ApiResponse<Boolean> {
        val response = apiDataSource.deleteProduct(productId)

        return if (response.isSuccess) {
            ApiResponse.Success(data = response.isSuccess)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

}
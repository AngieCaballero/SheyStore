package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.entities.toCartValueResponse
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : ICartRepository {
    override suspend fun getCartItems(documentId: String): ApiResponse<List<CartEntity>> {
        val response = apiDataSource.getCartItems(documentId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.fields?.toCartEntityList().orEmpty()
            ApiResponse.Success(data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun patchCartItems(
        documentId: String,
        cartItems: List<CartEntity>
    ): ApiResponse<List<CartEntity>> {
        val response = apiDataSource.patchCartItems(documentId, cartItems.toCartValueResponse())

        return if(response.isSuccess) {
            val data = response.getOrNull()?.fields?.value?.values?.values?.map {
                CartEntity(it.values.fields)
            }.orEmpty()
            ApiResponse.Success(data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
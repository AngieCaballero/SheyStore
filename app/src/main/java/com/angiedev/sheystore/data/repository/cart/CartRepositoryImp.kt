package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.entities.toShippingAddressValueResponse
import com.angiedev.sheystore.data.model.domain.entities.cart.CartEntity
import com.angiedev.sheystore.data.model.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartItemDTO
import javax.inject.Inject

class CartRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : ICartRepository {
    override suspend fun getCart(userId: Int): ApiResponse<CartEntity> {
        val response = apiDataSource.getCartItems(userId)

        return if (response.isSuccess) {
            val data = CartEntity(response.getOrNull()?.data)
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun addProductToCart(
        userId: Int,
        createCartItemDTO: CreateCartItemDTO
    ): ApiResponse<CartEntity> {
        val response = apiDataSource.addProductToCart(userId, createCartItemDTO)

        return if (response.isSuccess) {
            val data = CartEntity(response.getOrNull()?.data)
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun removeProductFromCart(
        userId: Int,
        cartItemId: Int
    ): ApiResponse<Boolean> {
        val response = apiDataSource.removeProductFromCart(userId, cartItemId)

        return if (response.isSuccess) {
            ApiResponse.Success(data = true)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getShippingAddress(documentId: String): ApiResponse<List<ShippingAddressEntity>> {
        val response = apiDataSource.getShippingAddress(documentId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.fields?.toShippingAddressEntityList().orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun patchShippingAddress(
        documentId: String,
        shippingAddressItems: List<ShippingAddressEntity>
    ): ApiResponse<List<ShippingAddressEntity>> {
        val response = apiDataSource.patchShippingAddress(documentId, shippingAddressItems.toShippingAddressValueResponse())

        return if (response.isSuccess) {
            val data = response.getOrNull()?.fields?.toShippingAddressEntityList().orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
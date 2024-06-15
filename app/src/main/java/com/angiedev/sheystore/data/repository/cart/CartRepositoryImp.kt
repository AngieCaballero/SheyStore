package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.request.order.CreateOrderDTO
import com.angiedev.sheystore.data.model.remote.request.paymentMethod.CreatePaymentMethodDTO
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.order.OrderEntity
import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity
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

    override suspend fun getShippingAddress(userId: Int): ApiResponse<List<ShippingAddressEntity>> {
        val response = apiDataSource.getShippingAddress(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                ShippingAddressEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun updateShippingAddress(
        userId: Int,
        shippingAddressId: Int,
        updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO
    ): ApiResponse<Boolean> {
        val response = apiDataSource.updateShippingAddress(userId, shippingAddressId, updateOrCreateShippingAddressDTO)

        return if (response.isSuccess) {
            ApiResponse.Success(data = true)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun createShippingAddress(
        userId: Int,
        updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO
    ): ApiResponse<Boolean> {
        val response = apiDataSource.createShippingAddress(userId, updateOrCreateShippingAddressDTO)

        return if (response.isSuccess) {
            ApiResponse.Success(data = true)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun createPaymentMethod(
        userId: Int,
        createPaymentMethodDTO: CreatePaymentMethodDTO
    ): ApiResponse<Boolean> {
        val response = apiDataSource.createPaymentMethod(userId, createPaymentMethodDTO)

        return if (response.isSuccess) {
            ApiResponse.Success(data = true)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getPaymentMethods(userId: Int): ApiResponse<List<PaymentMethodEntity>> {
        val response = apiDataSource.getPaymentMethod(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                PaymentMethodEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun createOrder(
        userId: Int,
        createOrderDTO: CreateOrderDTO
    ): ApiResponse<Boolean> {
        val response = apiDataSource.createOrder(userId, createOrderDTO)

        return if (response.isSuccess) {
            ApiResponse.Success(data = true)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun getOrders(userId: Int): ApiResponse<List<OrderEntity>> {
        val response = apiDataSource.getOrders(userId)

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                OrderEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
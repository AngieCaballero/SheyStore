package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.request.paymentMethod.CreatePaymentMethodDTO
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity

interface ICartRepository {
    suspend fun getCart(userId: Int) : ApiResponse<CartEntity>

    suspend fun addProductToCart(userId: Int, createCartItemDTO: CreateCartItemDTO): ApiResponse<CartEntity>

    suspend fun removeProductFromCart(userId: Int, cartItemId: Int): ApiResponse<Boolean>

    suspend fun getShippingAddress(userId: Int) : ApiResponse<List<ShippingAddressEntity>>

    suspend fun updateShippingAddress(userId: Int, shippingAddressId: Int, updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO): ApiResponse<Boolean>

    suspend fun createShippingAddress(userId: Int, updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO): ApiResponse<Boolean>

    suspend fun createPaymentMethod(userId: Int, createPaymentMethodDTO: CreatePaymentMethodDTO) : ApiResponse<Boolean>

    suspend fun getPaymentMethods(userId: Int) : ApiResponse<List<PaymentMethodEntity>>
}
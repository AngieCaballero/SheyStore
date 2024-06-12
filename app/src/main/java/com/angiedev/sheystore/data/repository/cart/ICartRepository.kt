package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface ICartRepository {
    suspend fun getCart(userId: Int) : ApiResponse<CartEntity>

    suspend fun addProductToCart(userId: Int, createCartItemDTO: CreateCartItemDTO): ApiResponse<CartEntity>

    suspend fun removeProductFromCart(userId: Int, cartItemId: Int): ApiResponse<Boolean>

    suspend fun getShippingAddress(userId: Int) : ApiResponse<List<ShippingAddressEntity>>
}
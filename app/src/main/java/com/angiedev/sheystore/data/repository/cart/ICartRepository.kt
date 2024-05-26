package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.CartValueResponse
import com.angiedev.sheystore.data.model.remote.response.DocumentCartResponse

interface ICartRepository {
    suspend fun getCartItems(documentId: String) : ApiResponse<List<CartEntity>>

    suspend fun patchCartItems(documentId: String, cartItems: List<CartEntity>) : ApiResponse<List<CartEntity>>

    suspend fun getShippingAddress(documentId: String) : ApiResponse<List<ShippingAddressEntity>>

    suspend fun patchShippingAddress(documentId: String, shippingAddressItems: List<ShippingAddressEntity>) : ApiResponse<List<ShippingAddressEntity>>
}
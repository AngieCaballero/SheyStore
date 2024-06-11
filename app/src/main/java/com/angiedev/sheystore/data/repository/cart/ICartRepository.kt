package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.model.domain.entities.cart.CartEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface ICartRepository {
    suspend fun getCart(userId: Int) : ApiResponse<CartEntity>

    suspend fun getShippingAddress(documentId: String) : ApiResponse<List<ShippingAddressEntity>>

    suspend fun patchShippingAddress(documentId: String, shippingAddressItems: List<ShippingAddressEntity>) : ApiResponse<List<ShippingAddressEntity>>
}
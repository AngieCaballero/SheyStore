package com.angiedev.sheystore.data.repository.cart

import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface ICartRepository {
    suspend fun getCartItems(documentId: String) : ApiResponse<List<CartEntity>>
}
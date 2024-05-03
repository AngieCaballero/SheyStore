package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IProductRepository {

    suspend fun getProducts(): ApiResponse<List<ProductEntity>>

    suspend fun getProductDetails(productId: String): ApiResponse<ProductDetailsEntity>
}
package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IProductRepository {

    suspend fun getProducts(): ApiResponse<List<ProductEntity>>

    suspend fun getProductDetails(productId: Int): ApiResponse<ProductDetailsEntity>
}
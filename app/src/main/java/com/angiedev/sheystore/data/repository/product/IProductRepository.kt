package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IProductRepository {

    suspend fun getProducts(): ApiResponse<List<ProductEntity>>

    suspend fun saveProduct(userId: Int, product: ProductEntity): ApiResponse<Boolean>

    suspend fun getProductsBySeller(userId: Int): ApiResponse<List<ProductEntity>>

    suspend fun updateProduct(product: ProductEntity): ApiResponse<Boolean>

    suspend fun deleteProduct(productId: Int): ApiResponse<Boolean>

}
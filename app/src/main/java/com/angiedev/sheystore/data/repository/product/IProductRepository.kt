package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IProductRepository {

    suspend fun getProducts(): ApiResponse<List<ProductEntity>>

    suspend fun saveProduct(product: ProductEntity): ApiResponse<Boolean>
}
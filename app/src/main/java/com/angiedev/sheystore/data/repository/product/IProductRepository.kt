package com.angiedev.sheystore.data.repository.product

import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse

interface IProductRepository {

    suspend fun getProducts(): ApiResponse<List<ProductEntity>>
}
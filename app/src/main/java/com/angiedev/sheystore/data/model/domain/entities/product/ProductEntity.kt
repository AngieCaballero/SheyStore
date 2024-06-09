package com.angiedev.sheystore.data.model.domain.entities.product

import com.angiedev.sheystore.data.model.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductDTO

data class ProductEntity(
    val id: Int,
    val name: String,
    val image: String,
    val price: Double,
    val discount: Double,
    val category: CategoryEntity,
    val rate: String
) {
    constructor(productDTO: ProductDTO?) : this(
        id = productDTO?.id ?: 0,
        name = productDTO?.name.orEmpty(),
        image = productDTO?.image.orEmpty(),
        price = productDTO?.price ?: 0.0,
        discount = productDTO?.discount ?: 0.0,
        category = CategoryEntity(productDTO?.category),
        rate = productDTO?.rate.orEmpty()
    )
}
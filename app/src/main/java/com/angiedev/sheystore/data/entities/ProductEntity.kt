package com.angiedev.sheystore.data.entities

import com.angiedev.sheystore.data.model.remote.ProductResponse

data class ProductEntity(
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
    val discount: String,
    val category: String,
    val rate: String
) {
    constructor(productResponse: ProductResponse?) : this(
        id = productResponse?.id?.stringValue.orEmpty(),
        name = productResponse?.name?.stringValue.orEmpty(),
        image = productResponse?.image?.stringValue.orEmpty(),
        price = productResponse?.price?.doubleValue ?: 0.0,
        discount = productResponse?.discount?.integerValue.orEmpty(),
        category = productResponse?.category?.stringValue.orEmpty(),
        rate = productResponse?.rate?.stringValue.orEmpty()
    )
}
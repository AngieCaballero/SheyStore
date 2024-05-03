package com.angiedev.sheystore.data.entities

import com.angiedev.sheystore.data.model.remote.response.ProductDetailsResponse

data class ProductDetailsEntity(
    val documentId: String,
    val id: String,
    val images: List<String>,
    val name: String,
    val category: String,
    val rating: String,
    val price: String,
    val description: String
) {
    constructor(productDetailsResponse: ProductDetailsResponse?, documentId: String?) : this(
        documentId = documentId.orEmpty().split("/").last(),
        id = productDetailsResponse?.id?.stringValue.orEmpty(),
        images = productDetailsResponse?.images?.arrayValue?.values?.map { it.stringValue }.orEmpty(),
        name = productDetailsResponse?.name?.stringValue.orEmpty(),
        category = productDetailsResponse?.category?.stringValue.orEmpty(),
        rating = productDetailsResponse?.rating?.stringValue.orEmpty(),
        price = productDetailsResponse?.price?.stringValue.orEmpty(),
        description = productDetailsResponse?.description?.stringValue.orEmpty()
    )
}
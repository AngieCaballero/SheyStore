package com.angiedev.sheystore.domain.entities.product

import android.os.Parcelable
import com.angiedev.sheystore.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductDTO
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val id: Int,
    val name: String,
    val image: String,
    val price: Double,
    val discount: Double,
    val category: CategoryEntity,
    val rate: String,
    val description: String,
    val presentationImages: List<String>
) : Parcelable {
    constructor(productDTO: ProductDTO?) : this(
        id = productDTO?.id ?: 0,
        name = productDTO?.name.orEmpty(),
        image = productDTO?.image.orEmpty(),
        price = productDTO?.price ?: 0.0,
        discount = productDTO?.discount ?: 0.0,
        category = CategoryEntity(productDTO?.category),
        rate = productDTO?.rate.orEmpty(),
        description = productDTO?.description.orEmpty(),
        presentationImages = productDTO?.presentationImages.orEmpty()
    )
}
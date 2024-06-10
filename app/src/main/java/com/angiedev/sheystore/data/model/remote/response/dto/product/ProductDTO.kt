package com.angiedev.sheystore.data.model.remote.response.dto.product

import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryDTO
import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("discount") val discount: Double?,
    @SerializedName("category") val category: CategoryDTO?,
    @SerializedName("rate") val rate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("presentation_images") val presentationImages: List<String>?
)

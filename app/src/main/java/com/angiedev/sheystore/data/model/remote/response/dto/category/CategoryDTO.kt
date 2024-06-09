package com.angiedev.sheystore.data.model.remote.response.dto.category

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?
)

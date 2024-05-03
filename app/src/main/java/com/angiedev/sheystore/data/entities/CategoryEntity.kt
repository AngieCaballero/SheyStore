package com.angiedev.sheystore.data.entities

import com.angiedev.sheystore.data.model.remote.response.CategoryResponse

data class CategoryEntity(
    val name: String,
    val image: String
) {
    constructor(categoryResponse: CategoryResponse?) : this(
        name = categoryResponse?.name?.stringValue.orEmpty(),
        image = categoryResponse?.image?.stringValue.orEmpty()
    )
}
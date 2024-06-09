package com.angiedev.sheystore.data.model.domain.entities.category

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryEntity(
    val id: Int,
    val name: String,
    val image: String
) : Parcelable {
    constructor(categoryDTO: CategoryDTO?) : this(
        id = categoryDTO?.id ?: 0,
        name = categoryDTO?.name.orEmpty(),
        image = categoryDTO?.image.orEmpty()
    )
}

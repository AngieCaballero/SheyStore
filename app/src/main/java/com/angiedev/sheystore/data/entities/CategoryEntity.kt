package com.angiedev.sheystore.data.entities

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.CategoryResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryEntity(
    val name: String,
    val image: String
) : Parcelable {
    constructor(categoryResponse: CategoryResponse?) : this(
        name = categoryResponse?.name?.stringValue.orEmpty(),
        image = categoryResponse?.image?.stringValue.orEmpty()
    )
}
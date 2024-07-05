package com.angiedev.sheystore.data.model.remote.response.dto.report.topCategories

import com.google.gson.annotations.SerializedName

data class TopCategoriesDTO(
    @SerializedName("category") val category: String?,
    @SerializedName("totalQuantity") val totalQuantity: Int?
)

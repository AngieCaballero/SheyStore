package com.angiedev.sheystore.data.model.remote.response.dto.report.productSoldQuantity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProductSoldQuantityDTO(
    @SerializedName("total_quantity") val totalQuantity: Int?,
    @SerializedName("date") val date: Date?
)

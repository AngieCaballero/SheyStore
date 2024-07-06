package com.angiedev.sheystore.domain.entities.report.productSoldQuantity

import com.angiedev.sheystore.data.model.remote.response.dto.report.productSoldQuantity.ProductSoldQuantityDTO
import java.util.Date

data class ProductSoldQuantityEntity(
    val totalQuantity: Int,
    val date: Date
) {
    constructor(productSoldQuantityDTO: ProductSoldQuantityDTO?) : this(
        totalQuantity = productSoldQuantityDTO?.totalQuantity ?: 0,
        date = productSoldQuantityDTO?.date ?: Date()
    )
}

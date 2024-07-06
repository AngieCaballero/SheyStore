package com.angiedev.sheystore.domain.entities.report.productSoldQuantity

import com.angiedev.sheystore.data.model.remote.response.dto.report.productSoldQuantity.ProductSoldQuantityDTO
import java.util.Date

data class ProductSoldQuantityEntity(
    val totalQuantity: Pair<Int, Int>,
    val date: Pair<Date, Int>
) {
    constructor(productSoldQuantityDTO: ProductSoldQuantityDTO?) : this(
        totalQuantity = Pair(productSoldQuantityDTO?.totalQuantity ?: 0, 0),
        date = Pair(productSoldQuantityDTO?.date ?: Date(), 0)
    )

    constructor(productSoldQuantityDTO: ProductSoldQuantityDTO, index: Int) : this(
        totalQuantity = Pair(productSoldQuantityDTO.totalQuantity ?: 0, index),
        date = Pair(productSoldQuantityDTO.date ?: Date(), index)
    )
}

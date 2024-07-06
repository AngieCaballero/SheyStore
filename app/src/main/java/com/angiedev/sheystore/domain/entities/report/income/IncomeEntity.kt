package com.angiedev.sheystore.domain.entities.report.income

import com.angiedev.sheystore.data.model.remote.response.dto.report.income.IncomeDTO
import java.util.Date

data class IncomeEntity(
    val totalPrice: Double,
    val date: Date
) {
    constructor(incomeDTO: IncomeDTO?) : this(
        totalPrice = incomeDTO?.totalPrice ?: 0.0,
        date = incomeDTO?.date ?: Date()
    )
}

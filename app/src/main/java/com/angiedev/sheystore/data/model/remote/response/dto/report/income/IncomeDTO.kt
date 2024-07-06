package com.angiedev.sheystore.data.model.remote.response.dto.report.income

import com.google.gson.annotations.SerializedName
import java.util.Date

data class IncomeDTO(
    @SerializedName("total_price") val totalPrice: Double?,
    @SerializedName("date") val date: Date?
)

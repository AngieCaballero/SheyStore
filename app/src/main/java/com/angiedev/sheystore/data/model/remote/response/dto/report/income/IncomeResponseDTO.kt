package com.angiedev.sheystore.data.model.remote.response.dto.report.income

data class IncomeResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<IncomeDTO>?
)

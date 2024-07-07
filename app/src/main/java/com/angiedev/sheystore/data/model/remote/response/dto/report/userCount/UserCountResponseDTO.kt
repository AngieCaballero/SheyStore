package com.angiedev.sheystore.data.model.remote.response.dto.report.userCount

data class UserCountResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<UserCountDTO>?
)

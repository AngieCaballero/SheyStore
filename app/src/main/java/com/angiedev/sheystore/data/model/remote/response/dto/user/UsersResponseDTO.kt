package com.angiedev.sheystore.data.model.remote.response.dto.user

data class UsersResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: List<UserDTO>?
)

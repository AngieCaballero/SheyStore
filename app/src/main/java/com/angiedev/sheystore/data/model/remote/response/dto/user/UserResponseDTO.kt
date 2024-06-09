package com.angiedev.sheystore.data.model.remote.response.dto.user

data class UserResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: UserDTO?
)

package com.angiedev.sheystore.data.model.remote.request

import com.angiedev.sheystore.data.model.remote.response.StringResponse

data class CreateUserFields(
    val fullName: StringResponse,
    val gender: StringResponse,
    val phone: StringResponse,
    val photo: StringResponse,
    val role: StringResponse,
    val username: StringResponse
)
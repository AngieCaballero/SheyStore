package com.angiedev.sheystore.data.model.remote.request

import com.angiedev.sheystore.data.model.remote.response.StringResponse

data class CreateUserFields(
    val lastname: StringResponse,
    val name: StringResponse,
    val role: StringResponse,
    val username: StringResponse
)
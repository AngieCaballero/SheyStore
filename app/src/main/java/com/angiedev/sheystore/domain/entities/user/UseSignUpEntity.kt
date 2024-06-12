package com.angiedev.sheystore.domain.entities.user

import com.angiedev.sheystore.data.model.remote.response.dto.user.SignUpDTO

data class UseSignUpEntity(
    val id: Int,
    val email: String,
    val token: String
) {
    constructor(userDTO: SignUpDTO?) : this (
        id = userDTO?.id?.toInt() ?: 0,
        email = userDTO?.email ?: "",
        token = userDTO?.token ?: ""
    )
}

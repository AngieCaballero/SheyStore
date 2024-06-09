package com.angiedev.sheystore.data.model.domain.entities.user

import com.angiedev.sheystore.data.model.remote.response.dto.user.SignInDTO

data class SignInEntity(
    val id: Int,
    val email: String,
    val token: String,
    val username: String,
    val fullName: String,
    val gender: String,
    val phone: String,
    val photo: String,
    val role: String
) {
    constructor(signInDTO: SignInDTO?) : this(
        id = signInDTO?.id?.toInt() ?: 0,
        email = signInDTO?.email.orEmpty(),
        token = signInDTO?.token.orEmpty(),
        username = signInDTO?.username.orEmpty(),
        fullName = signInDTO?.fullName.orEmpty(),
        gender = signInDTO?.gender.orEmpty(),
        phone = signInDTO?.phone.orEmpty(),
        photo = signInDTO?.photo.orEmpty(),
        role = signInDTO?.role.orEmpty()
    )
}

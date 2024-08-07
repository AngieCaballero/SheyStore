package com.angiedev.sheystore.domain.entities.user

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val id: Int,
    val fullName: String,
    val gender: String,
    val phone: String,
    val photo: String,
    val role: String,
    val username: String
) : Parcelable {
    constructor(userDTO: UserDTO?) : this(
        id = userDTO?.id ?: 0,
        fullName = userDTO?.fullName.orEmpty(),
        gender = userDTO?.gender.orEmpty(),
        phone = userDTO?.phone.orEmpty(),
        photo = userDTO?.photo.orEmpty(),
        role = userDTO?.role.orEmpty(),
        username = userDTO?.username.orEmpty()
    )
}

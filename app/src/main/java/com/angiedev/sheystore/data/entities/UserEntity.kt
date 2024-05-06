package com.angiedev.sheystore.data.entities

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.UserResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val fullName: String,
    val gender: String,
    val phone: String,
    val photo: String,
    val role: String,
    val username: String
) : Parcelable {
    constructor(userResponse: UserResponse?) : this(
        fullName = userResponse?.fullName?.stringValue.orEmpty(),
        gender = userResponse?.gender?.stringValue.orEmpty(),
        phone = userResponse?.phone?.stringValue.orEmpty(),
        photo = userResponse?.phone?.stringValue.orEmpty(),
        role = userResponse?.role?.stringValue.orEmpty(),
        username = userResponse?.username?.stringValue.orEmpty()
    )
}

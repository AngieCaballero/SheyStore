package com.angiedev.sheystore.data.entities

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.UserResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val username: String,
    val role: String,
    val lastname: String,
    val name: String
) : Parcelable {
    constructor(userResponse: UserResponse?) : this(
        username = userResponse?.username?.stringValue.orEmpty(),
        role = userResponse?.role?.stringValue.orEmpty(),
        lastname = userResponse?.lastname?.stringValue.orEmpty(),
        name = userResponse?.name?.stringValue.orEmpty()
    )
}

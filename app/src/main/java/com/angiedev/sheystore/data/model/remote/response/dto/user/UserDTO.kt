package com.angiedev.sheystore.data.model.remote.response.dto.user

import com.angiedev.sheystore.domain.entities.user.UserEntity
import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("role") val role: String,
    @SerializedName("username") val username: String
)

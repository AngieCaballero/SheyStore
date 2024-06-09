package com.angiedev.sheystore.data.model.remote.response.dto.user

import com.google.gson.annotations.SerializedName

data class SignUpDTO(
    @SerializedName("id") val id: Double?,
    @SerializedName("email") val email: String?,
    @SerializedName("token") val token: String
)

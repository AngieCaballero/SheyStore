package com.angiedev.sheystore.data.model.remote.response.dto.user

import com.google.gson.annotations.SerializedName

data class SignInDTO(
    @SerializedName("id") val id: Double?,
    @SerializedName("email") val email: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

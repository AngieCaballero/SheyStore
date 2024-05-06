package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("fullName") val fullName: StringResponse,
    @SerializedName("gender") val gender: StringResponse,
    @SerializedName("phone") val phone: StringResponse,
    @SerializedName("photo") val photo: StringResponse,
    @SerializedName("role") val role: StringResponse,
    @SerializedName("username") val username: StringResponse
) : Parcelable

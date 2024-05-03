package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("username") val username: StringResponse?,
    @SerializedName("role") val role: StringResponse?,
    @SerializedName("lastname") val lastname: StringResponse?,
    @SerializedName("name") val name: StringResponse?
) : Parcelable

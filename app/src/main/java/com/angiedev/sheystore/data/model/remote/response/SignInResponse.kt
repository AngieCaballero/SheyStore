package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class SignInResponse (
    @SerializedName("kind") val kind: String?,
    @SerializedName("localId") val localId: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("idToken") val idToken: String?,
    @SerializedName("refreshToken") val refreshToken: String?,
    @SerializedName("expiresIn") val expiresIn: String?,
    @SerializedName("registered") val registered: Boolean?,
    @SerializedName("error") val error: StatusCode?
) : Parcelable
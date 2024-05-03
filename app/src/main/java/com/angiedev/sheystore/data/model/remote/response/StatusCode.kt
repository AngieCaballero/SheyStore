package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusCode(
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String?
) : Parcelable
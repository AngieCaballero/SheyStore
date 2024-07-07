package com.angiedev.sheystore.data.model.remote.response.dto.report.userCount

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserCountDTO(
    @SerializedName("date") val date: Date?,
    @SerializedName("count") val count: Int?
)

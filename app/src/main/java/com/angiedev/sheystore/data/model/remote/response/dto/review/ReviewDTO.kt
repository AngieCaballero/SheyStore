package com.angiedev.sheystore.data.model.remote.response.dto.review

import com.google.gson.annotations.SerializedName

data class ReviewDTO(
    @SerializedName("id") val id: Int?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("rating") val rating: Float?,
    @SerializedName("image") val image: String?
)

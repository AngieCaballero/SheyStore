package com.angiedev.sheystore.data.model.remote.request.review

import com.google.gson.annotations.SerializedName

data class CreateReviewDTO(
    @SerializedName("comment") val comment: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("image") val image: String?
)

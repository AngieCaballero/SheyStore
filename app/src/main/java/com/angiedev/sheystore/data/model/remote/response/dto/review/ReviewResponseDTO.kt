package com.angiedev.sheystore.data.model.remote.response.dto.review

data class ReviewResponseDTO(
    val status: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val data: ReviewDTO?
)

package com.angiedev.sheystore.domain.entities.review

import com.angiedev.sheystore.data.model.remote.response.dto.review.ReviewDTO

data class ReviewEntity(
    val id: Int,
    val comment: String,
    val rating: Float,
    val image: String
) {
    constructor(reviewDTO: ReviewDTO?) : this(
        id = reviewDTO?.id ?: 0,
        comment = reviewDTO?.comment.orEmpty(),
        rating = reviewDTO?.rating ?: 0f,
        image = reviewDTO?.image.orEmpty()
    )
}

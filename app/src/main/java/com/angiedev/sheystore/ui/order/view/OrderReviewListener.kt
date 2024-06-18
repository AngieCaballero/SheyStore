package com.angiedev.sheystore.ui.order.view

interface OrderReviewListener {
    fun onSubmit(rating: Float, comment: String, image: String)
}
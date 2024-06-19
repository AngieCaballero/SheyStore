package com.angiedev.sheystore.ui.modules.buyer.order.view

interface OrderReviewListener {
    fun onSubmit(rating: Float, comment: String, image: String)
}
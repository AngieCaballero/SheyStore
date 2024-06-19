package com.angiedev.sheystore.ui.modules.buyer.order.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.review.CreateReviewDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import com.angiedev.sheystore.domain.entities.order.OrderEntity
import com.google.android.gms.common.api.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _order = MutableLiveData<ApiResponse<List<OrderEntity>>>()
    val order get() = _order

    private val _review = MutableLiveData<ApiResponse<Boolean>>()
    val review get() = _review

    fun getOrders(userId: Int, orderStatus: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getOrders(userId, orderStatus)

            _order.postValue(response)
        }
    }

    fun sendReview(userId: Int, productId: Int, comment: String, rating: Float, image: String?) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.sendReview(
                userId, productId, CreateReviewDTO(
                    comment, rating, image
                )
            )
            _review.postValue(response)
        }
    }
}
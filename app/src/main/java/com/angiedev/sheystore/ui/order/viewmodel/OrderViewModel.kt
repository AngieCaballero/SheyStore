package com.angiedev.sheystore.ui.order.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import com.angiedev.sheystore.domain.entities.order.OrderEntity
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

    fun getOrders(userId: Int, orderStatus: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getOrders(userId, orderStatus)

            _order.postValue(response)
        }
    }
}
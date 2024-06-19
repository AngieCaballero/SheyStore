package com.angiedev.sheystore.ui.modules.buyer.paymentConfirm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.order.CreateOrderDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PaymentConfirmViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _paymentConfirm = MutableLiveData<ApiResponse<Boolean>>()
    val paymentConfirm get() = _paymentConfirm

    fun createOrder(userId: Int, cartId: Int, status: String) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.createOrder(
                userId = userId,
                createOrderDTO = CreateOrderDTO(
                    orderStatus = status,
                    cartId = cartId
                )
            )
            _paymentConfirm.postValue(response)
        }
    }
}
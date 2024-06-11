package com.angiedev.sheystore.ui.checkout.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.model.domain.entities.cart.CartEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _shippingAddress = MutableLiveData<ApiResponse<List<ShippingAddressEntity>>>()
    val shippingAddress get() = _shippingAddress

    private val _orderList = MutableLiveData<ApiResponse<CartEntity>>()
    val orderList get() = _orderList

    fun getOrderList(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getCart(userId)
            _orderList.postValue(response)
        }
    }

    fun getShippingAddress(documentId: String) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getShippingAddress(documentId)
            _shippingAddress.postValue(response)
        }
    }
}
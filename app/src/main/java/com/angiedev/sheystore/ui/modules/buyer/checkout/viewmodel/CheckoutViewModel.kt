package com.angiedev.sheystore.ui.modules.buyer.checkout.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
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

    fun getShippingAddress(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getShippingAddress(userId)
            _shippingAddress.postValue(response)
        }
    }
}
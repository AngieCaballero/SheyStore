package com.angiedev.sheystore.ui.paymentMethods.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PaymentMethodsViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {
    private val _paymentMethods = MutableLiveData<ApiResponse<List<PaymentMethodEntity>>>()
    val paymentMethods get() = _paymentMethods

    fun getPaymentMethods(
        userId: Int
    ) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getPaymentMethods(
                userId = userId
            )
            _paymentMethods.postValue(response)
        }
    }
}
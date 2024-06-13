package com.angiedev.sheystore.ui.addNewCard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.paymentMethod.CreatePaymentMethodDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddNewCardViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _paymentMethodCreated = MutableLiveData<ApiResponse<Boolean>>()
    val paymentMethodCreated get() =  _paymentMethodCreated

    fun createPaymentMethod(
        userId: Int,
        cardName: String,
        cardNumber: String,
        expiredAt: String,
        cvcNumber: Int
    ) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.createPaymentMethod(
                userId = userId,
                createPaymentMethodDTO = CreatePaymentMethodDTO(
                    cardName = cardName,
                    cardNumber = cardNumber,
                    expiredAt = expiredAt,
                    cvcNumber = cvcNumber
                )
            )
            _paymentMethodCreated.postValue(response)
        }
    }
}
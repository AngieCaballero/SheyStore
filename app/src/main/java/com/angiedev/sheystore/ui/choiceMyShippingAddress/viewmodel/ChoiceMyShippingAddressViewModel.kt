package com.angiedev.sheystore.ui.choiceMyShippingAddress.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ChoiceMyShippingAddressViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _shippingAddressList = MutableLiveData<ApiResponse<List<ShippingAddressEntity>>>()
    val shippingAddressList get() = _shippingAddressList

    fun getShippingAddressList(documentId: String) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getShippingAddress(documentId)
            _shippingAddressList.postValue(response)
        }
    }

    fun updateShippingAddress(documentId: String, newAddressShipping: List<ShippingAddressEntity>) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.patchShippingAddress(documentId, newAddressShipping)
            _shippingAddressList.postValue(response)
        }
    }
}
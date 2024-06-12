package com.angiedev.sheystore.ui.addNewAddress.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddNewAddressViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _shippingAddressCrated = MutableLiveData<ApiResponse<Boolean>>()
    val shippingAddressCrated get() = _shippingAddressCrated

    fun createShippingAddress(
        userId: Int,
        name: String,
        details: String,
        default: Boolean,
    ) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.createShippingAddress(
                userId = userId,
                updateOrCreateShippingAddressDTO = UpdateOrCreateShippingAddressDTO(
                    name = name,
                    details = details,
                    default = default
                )
            )
            _shippingAddressCrated.postValue(response)
        }
    }
}
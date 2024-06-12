package com.angiedev.sheystore.ui.choiceMyShippingAddress.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
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

    private val _shippingAddressUpdated = MutableLiveData<ApiResponse<Boolean>>()
    val shippingAddressUpdated get() = _shippingAddressUpdated

    fun getShippingAddressList(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getShippingAddress(userId)
            _shippingAddressList.postValue(response)
        }
    }

    fun updateShippingAddress(
        userId: Int,
        shippingAddressEntity: ShippingAddressEntity?
    ) {
        if (shippingAddressEntity == null) return
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.updateShippingAddress(
                userId = userId,
                shippingAddressId = shippingAddressEntity.id,
                updateOrCreateShippingAddressDTO = UpdateOrCreateShippingAddressDTO(
                    name = shippingAddressEntity.name,
                    details = shippingAddressEntity.details,
                    default = shippingAddressEntity.default
                )
            )
            _shippingAddressUpdated.postValue(response)
        }
    }
}
package com.angiedev.sheystore.ui.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<ApiResponse<List<CartEntity>>>()
    val cartItems get() = _cartItems

    fun getCartItems(documentId: String) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getCartItems(documentId)
            _cartItems.postValue(response)
        }
    }

}
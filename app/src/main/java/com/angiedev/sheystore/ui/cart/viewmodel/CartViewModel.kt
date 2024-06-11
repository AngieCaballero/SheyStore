package com.angiedev.sheystore.ui.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.domain.entities.cart.CartEntity
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

    private val _cartItems = MutableLiveData<ApiResponse<CartEntity>>()
    val cartItems get() = _cartItems

    fun getCartItems(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getCart(userId)
            _cartItems.postValue(response)
        }
    }

    fun patchCartItems(documentId: String, cartItems: List<CartEntity>) {

    }

}
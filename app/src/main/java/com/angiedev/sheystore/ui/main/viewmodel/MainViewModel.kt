package com.angiedev.sheystore.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.model.domain.CartItem
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cart = MutableLiveData<ApiResponse<List<CartEntity>>>()
    val cart get() = _cart

    val myCart = mutableListOf<CartEntity>()

    fun getCartItems(documentId: String) {
        runBlocking(Dispatchers.IO) {
            val cartItems = cartRepository.getCartItems(documentId)
            _cart.postValue(cartItems)
        }
    }

}
package com.angiedev.sheystore.ui.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
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

    private val _deleteCartItem = MutableLiveData<ApiResponse<Boolean>>()
    val deleteCartItem get() = _deleteCartItem

    fun getCartItems(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.getCart(userId)
            _cartItems.postValue(response)
        }
    }

    fun removeProductFromCart(userId: Int, cartItemId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.removeProductFromCart(userId, cartItemId)
            _deleteCartItem.postValue(response)
        }
    }

    fun addProductToCart(userId: Int, color: String, quantity: Int, totalPrice: Double, productId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = cartRepository.addProductToCart(
                userId = userId,
                createCartItemDTO = CreateCartItemDTO(
                    color = color,
                    quantity = quantity,
                    totalPrice = totalPrice,
                    productId = productId
                )
            )
            _cartItems.postValue(response)
        }
    }
}
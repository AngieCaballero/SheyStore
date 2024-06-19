package com.angiedev.sheystore.ui.modules.buyer.mostPopular.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.domain.entities.cart.CartEntity
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import com.angiedev.sheystore.data.repository.product.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: IProductRepository,
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<ApiResponse<CartEntity>>()
    val cartItems get() = _cartItems

    private val _products: MutableLiveData<ApiResponse<List<ProductEntity>>> = MutableLiveData()
    val products get() = _products

    private val _filteredProducts: MutableLiveData<List<ProductEntity>> = MutableLiveData()

    val filteredProducts get() = _filteredProducts


    fun getProducts() {
        runBlocking(Dispatchers.IO) {
            val response = productRepository.getProducts()
            _products.postValue(response)
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


    fun setProductsList(newList: List<ProductEntity>) {
        _filteredProducts.postValue(newList)
    }

    fun filterBy(query: String, productList: List<ProductEntity>) {
        var items = productList
        if (query != "Todo" && query.isNotBlank()) {
            items = items.filter {
                it.category.name.contains(query)
            }
        }
        _filteredProducts.postValue(items)
    }
}
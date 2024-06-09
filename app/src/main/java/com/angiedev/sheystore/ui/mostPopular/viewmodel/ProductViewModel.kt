package com.angiedev.sheystore.ui.mostPopular.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity
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
    private val categoryRepository: ICartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<ApiResponse<List<CartEntity>>>()
    val cartItems get() = _cartItems

    private val _products: MutableLiveData<ApiResponse<List<ProductEntity>>> = MutableLiveData()
    val products get() = _products

    private val _filteredProducts: MutableLiveData<List<ProductEntity>> = MutableLiveData()

    val filteredProducts get() = _filteredProducts

    private val _productDetails: MutableLiveData<ApiResponse<ProductDetailsEntity>> = MutableLiveData()
    val productDetails get() = _productDetails

    fun getProducts() {
        runBlocking(Dispatchers.IO) {
            val response = productRepository.getProducts()
            _products.postValue(response)
        }
    }

    fun putProductInCart(documentId: String, newList: MutableList<CartEntity>) {
        runBlocking(Dispatchers.IO) {
            val response = categoryRepository.patchCartItems(documentId, newList)
            _cartItems.postValue(response)
        }
    }

    fun getProductDetails(productId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = productRepository.getProductDetails(productId)
            _productDetails.postValue(response)
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
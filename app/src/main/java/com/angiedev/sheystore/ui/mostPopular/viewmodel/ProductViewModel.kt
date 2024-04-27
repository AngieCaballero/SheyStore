package com.angiedev.sheystore.ui.mostPopular.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.data.repository.product.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: IProductRepository
) : ViewModel() {

    private val _products: MutableLiveData<ApiResponse<List<ProductEntity>>> = MutableLiveData()
    val products get() = _products

    val filteredList = MutableLiveData<List<ProductEntity>>()

    private val _productDetails: MutableLiveData<ApiResponse<ProductDetailsEntity>> = MutableLiveData()
    val productDetails get() = _productDetails

    fun getProducts() {
        runBlocking(Dispatchers.IO) {
            val response = productRepository.getProducts()
            _products.postValue(response)
        }
    }

    fun getProductDetails(productId: String) {
        runBlocking(Dispatchers.IO) {
            val response = productRepository.getProductDetails(productId)
            _productDetails.postValue(response)
        }
    }

    fun setProductsList(newList: List<ProductEntity>) {
        filteredList.postValue(newList)
    }

    fun filterBy(query: String, originalList: MutableList<ProductEntity>) {
        val filtered = mutableListOf<ProductEntity>()
        filtered.addAll(filteredList.value.orEmpty())
        if (query == "Todos" || query.isBlank()) {
            filteredList.postValue(originalList)
            return
        }
        val filter = originalList.filter { it.category == query }
        filteredList.postValue(filter)
    }
}
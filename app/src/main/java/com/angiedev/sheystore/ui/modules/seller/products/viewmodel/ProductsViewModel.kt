package com.angiedev.sheystore.ui.modules.seller.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.product.IProductRepository
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: IProductRepository
): ViewModel() {

    private val _products: MutableLiveData<ApiResponse<List<ProductEntity>>> = MutableLiveData()
    val products get() = _products

    private val _productUpdated: MutableLiveData<ApiResponse<Boolean>> = MutableLiveData()
    val productUpdated get() = _productUpdated

    private val _productDeleted: MutableLiveData<ApiResponse<Boolean>> = MutableLiveData()
    val productDeleted get() = _productDeleted

    fun getProducts(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(productRepository.getProductsBySeller(userId))
        }
    }

    fun updateProduct(product: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _productUpdated.postValue(productRepository.updateProduct(product))
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _productDeleted.postValue(productRepository.deleteProduct(productId))
        }
    }

}
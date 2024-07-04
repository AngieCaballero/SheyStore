package com.angiedev.sheystore.ui.modules.seller.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.product.IProductRepository
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: IProductRepository
): ViewModel() {

    private val _products: MutableLiveData<ApiResponse<List<ProductEntity>>> = MutableLiveData()
    val products get() = _products



}
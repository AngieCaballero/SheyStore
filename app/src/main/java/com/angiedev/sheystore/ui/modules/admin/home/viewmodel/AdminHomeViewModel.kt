package com.angiedev.sheystore.ui.modules.admin.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val reportRepository: IReportRepository
) : ViewModel() {

    private val _productSoldGlobalQuantity = MutableLiveData<ApiResponse<List<ProductSoldQuantityEntity>>>()
    val productSoldGlobalQuantity get() = _productSoldGlobalQuantity

    fun getProductSoldGlobalQuantity() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getProductsSoldGlobalQuantity()
            _productSoldGlobalQuantity.postValue(response)
        }
    }

}
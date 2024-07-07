package com.angiedev.sheystore.ui.modules.admin.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.userCount.UserCountEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val reportRepository: IReportRepository
) : ViewModel() {

    private val _productSoldGlobalQuantity =
        MutableLiveData<ApiResponse<List<ProductSoldQuantityEntity>>>()
    val productSoldGlobalQuantity get() = _productSoldGlobalQuantity

    private val _usersReport = MutableLiveData<ApiResponse<List<UserCountEntity>>>()
    val usersReport get() = _usersReport

    fun getProductSoldGlobalQuantity() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getProductsSoldGlobalQuantity()
            _productSoldGlobalQuantity.postValue(response)
        }
    }

    fun getUsersReport() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getUsersReport()
            _usersReport.postValue(response)
        }
    }
}
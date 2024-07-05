package com.angiedev.sheystore.ui.modules.seller.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeSellerViewModel @Inject constructor (
    private val reportRepository: IReportRepository
) : ViewModel() {

    private val _topCategories = MutableLiveData<ApiResponse<List<TopCategoriesEntity>>>()
    val topCategories get() = _topCategories

    fun getTopCategories(userId: Int) {
         runBlocking(Dispatchers.IO) {
             val response = reportRepository.getTopCategories(userId)
             _topCategories.postValue(response)
         }
    }
}

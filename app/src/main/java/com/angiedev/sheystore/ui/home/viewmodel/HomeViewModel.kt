package com.angiedev.sheystore.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.data.entities.SpecialsOffersEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.data.repository.home.IHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: IHomeRepository
) : ViewModel() {

    private val _specialsOffers: MutableLiveData<ApiResponse<List<SpecialsOffersEntity>>> = MutableLiveData()
    val specialsOffers get() = _specialsOffers

    private val _categories: MutableLiveData<ApiResponse<List<CategoryEntity>>> = MutableLiveData()
    val categories get() = _categories

    fun getCategories() {
        _categories.postValue(ApiResponse.Loading)
        runBlocking(Dispatchers.IO) {
            val response = homeRepository.getCategories()
            _categories.postValue(response)
        }
    }

    fun getSpecialsOffers() {
        _specialsOffers.postValue(ApiResponse.Loading)
        runBlocking(Dispatchers.IO) {
            val response = homeRepository.getSpecialsOffers()
            _specialsOffers.postValue(response)
        }
    }
}
package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.data.entities.SpecialsOffersEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IHomeRepository {

    suspend fun getSpecialsOffers() : ApiResponse<List<SpecialsOffersEntity>>

    suspend fun getCategories() : ApiResponse<List<CategoryEntity>>
}
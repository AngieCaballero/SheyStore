package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.domain.entities.specialsOffers.SpecialOfferEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse

interface IHomeRepository {

    suspend fun getSpecialsOffers() : ApiResponse<List<SpecialOfferEntity>>

    suspend fun getCategories() : ApiResponse<List<CategoryEntity>>
}
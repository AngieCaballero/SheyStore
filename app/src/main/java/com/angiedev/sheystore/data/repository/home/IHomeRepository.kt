package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.data.entities.SpecialsOffersEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.data.model.remote.DocumentResponse
import com.angiedev.sheystore.data.model.remote.SpecialsOffersResponse

interface IHomeRepository {

    suspend fun getSpecialsOffers() : ApiResponse<List<SpecialsOffersEntity>>
}
package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.data.model.domain.entities.specialsOffers.SpecialOfferEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.util.parseArray
import com.google.gson.Gson
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : IHomeRepository {

    override suspend fun getSpecialsOffers(): ApiResponse<List<SpecialOfferEntity>> {
        val response = apiDataSource.getSpecialsOffers()

        return if (response.isSuccess) {
            // Retrieve data
            val data = response.getOrNull()?.data?.map {
                SpecialOfferEntity(it)
            }.orEmpty()
            ApiResponse.Success(data)
        } else {
            // Return Exceptions
            ApiResponse.Error(response.exceptionOrNull())
        }

    }

    override suspend fun getCategories(): ApiResponse<List<CategoryEntity>> {
        val response = apiDataSource.getCategories()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.documents?.map {
                CategoryEntity(parseArray(Gson().toJson(it.fields)))
            }?.toMutableList()
            ApiResponse.Success(data = data.orEmpty())
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
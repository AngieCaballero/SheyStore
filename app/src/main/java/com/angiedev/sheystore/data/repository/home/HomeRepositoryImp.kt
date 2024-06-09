package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.model.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.data.model.domain.entities.specialsOffers.SpecialOfferEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
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
            val data = response.getOrNull()?.data?.map {
                CategoryEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }
}
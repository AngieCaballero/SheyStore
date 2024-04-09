package com.angiedev.sheystore.data.repository.home

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.SpecialsOffersEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.data.util.parseArray
import com.google.gson.Gson
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val apiDataSource: ApiDataSource
) : IHomeRepository {

    override suspend fun getSpecialsOffers(): ApiResponse<List<SpecialsOffersEntity>> {
        val response = apiDataSource.getSpecialsOffers()

        return if (response.isSuccess) {
            // Retrieve data
            val data = response.getOrNull()?.documents?.map {
                SpecialsOffersEntity(parseArray(Gson().toJson(it.fields)))
            }
            ApiResponse.Success(data.orEmpty())
        } else {
            // Return Exceptions
            ApiResponse.Error(response.exceptionOrNull())
        }

    }
}
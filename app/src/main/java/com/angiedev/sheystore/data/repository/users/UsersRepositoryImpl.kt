package com.angiedev.sheystore.data.repository.users

import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.domain.entities.user.UserEntity
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource
) : UsersRepository {

    override suspend fun getAllUsers(): ApiResponse<List<UserEntity>> {
        val response = apiDataSource.getUsers()

        return if (response.isSuccess) {
            val data = response.getOrNull()?.data?.map {
                UserEntity(it)
            }.orEmpty()
            ApiResponse.Success(data = data)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun updateUser(userId: Int, userEntity: UserEntity): ApiResponse<Boolean> {
        val response = apiDataSource.saveUserProfileData(userId, userEntity)

        return if (response.isSuccess) {
            ApiResponse.Success(data = response.isSuccess)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

    override suspend fun deleteUser(userId: Int): ApiResponse<Boolean> {
        val response = apiDataSource.deleteUser(userId)

        return if (response.isSuccess) {
            ApiResponse.Success(data = response.isSuccess)
        } else {
            ApiResponse.Error(response.exceptionOrNull())
        }
    }

}
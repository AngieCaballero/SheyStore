package com.angiedev.sheystore.data.repository.users

import com.angiedev.sheystore.data.datasource.remote.UsersMockDataSource
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.user.UserEntity
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    private val dataSource = UsersMockDataSource()

    override suspend fun getAllUsers(): ApiResponse<List<UserEntity>> {
        return ApiResponse.Success(dataSource.getUsers())
    }

    override suspend fun getUserById(username: String): ApiResponse<UserEntity> {
        val response = dataSource.getUser(username = username)
        return if (response != null) ApiResponse.Success(response)
        else ApiResponse.Error(null)
    }

    override suspend fun createUser(userEntity: UserEntity) = dataSource.addUser(userEntity)

    override suspend fun updateUser(username: String, userEntity: UserEntity) =
        dataSource.updateUser(username, userEntity)

    override suspend fun deleteUser(username: String) = dataSource.deleteUser(username)

}
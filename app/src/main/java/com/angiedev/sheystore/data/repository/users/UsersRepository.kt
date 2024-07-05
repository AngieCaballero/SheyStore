package com.angiedev.sheystore.data.repository.users

import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.user.UserEntity

interface UsersRepository {

    suspend fun getAllUsers(): ApiResponse<List<UserEntity>>

    suspend fun getUserById(username: String): ApiResponse<UserEntity>

    suspend fun createUser(userEntity: UserEntity): Boolean

    suspend fun updateUser(username: String, userEntity: UserEntity): Boolean

    suspend fun deleteUser(username: String): Boolean
}
package com.angiedev.sheystore.data.repository.users

import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.domain.entities.user.UserEntity

interface UsersRepository {

    suspend fun getAllUsers(): ApiResponse<List<UserEntity>>

    suspend fun updateUser(userId: Int, userEntity: UserEntity): ApiResponse<Boolean>

    suspend fun deleteUser(userId: Int): ApiResponse<Boolean>
}
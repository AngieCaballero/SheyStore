package com.angiedev.sheystore.data.repository.auth

import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.util.AuthResource

interface IAuthenticationRepository {

    suspend fun isAuthenticate(currentTime: Long): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResource<Boolean>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResource<Boolean>

    suspend fun signOut()

    suspend fun saveUserProfileData(userDTO: UserDTO, userId: Int) : AuthResource<Boolean>
}
package com.angiedev.sheystore.data.repository.auth

import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.domain.entities.user.SignInEntity
import com.angiedev.sheystore.domain.entities.user.UseSignUpEntity
import com.angiedev.sheystore.domain.entities.user.UserEntity

interface IAuthenticationRepository {

    suspend fun isAuthenticate(currentTime: Long): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResource<UseSignUpEntity>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResource<SignInEntity>

    suspend fun signOut(): Boolean

    suspend fun saveUserProfileData(user: UserEntity, userId: Int) : AuthResource<Boolean>
}
package com.angiedev.sheystore.data.repository.auth

import com.angiedev.sheystore.data.datasource.local.DataStoreManager
import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.domain.entities.user.UserEntity
import com.angiedev.sheystore.domain.entities.user.SignInEntity
import com.angiedev.sheystore.domain.entities.user.UseSignUpEntity
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val apiDataSource: ApiDataSource
) : IAuthenticationRepository {

    override suspend fun isAuthenticate(currentTime: Long): Boolean {
        var isAuthenticated = ""
        dataStoreManager.readValue(PreferencesKeys.TOKEN) {
            isAuthenticated = this
        }
        return isAuthenticated.isNotBlank()
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResource<Boolean> {
        val authResult = apiDataSource.createAccount(email, password)
        val responseData = authResult.getOrNull()

        return if (authResult.isSuccess && responseData != null) {
            val data = UseSignUpEntity(responseData.data)
            with(dataStoreManager) {
                storeValue(PreferencesKeys.USER_ID, data.id)
                storeValue(PreferencesKeys.TOKEN, data.token)
                storeValue(PreferencesKeys.EMAIL, data.email)
            }
            AuthResource.Success(true)
        } else {
            AuthResource.Error(authResult.exceptionOrNull()?.toString() ?: "Ha ocurrido un error al intentar crear la cuenta")
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResource<SignInEntity> {
        val authResult = apiDataSource.signInWithPassword(email, password)
        val response = authResult.getOrNull()
        val responseData = response?.data

        return if (authResult.isSuccess && responseData != null) {
            val user = SignInEntity(responseData)
            with(dataStoreManager) {
                storeValue(PreferencesKeys.USER_ID, user.id)
                storeValue(PreferencesKeys.TOKEN, user.token)
                storeValue(PreferencesKeys.PASSWORD, password)
                storeValue(PreferencesKeys.EMAIL, user.email)
                storeValue(PreferencesKeys.ROLE, user.role)
                storeValue(PreferencesKeys.USERNAME, user.username)
                storeValue(PreferencesKeys.FULL_NAME, user.fullName)
                storeValue(PreferencesKeys.PHOTO, user.photo)
                storeValue(PreferencesKeys.PHONE, user.phone)
            }
            AuthResource.Success(SignInEntity(responseData))
        } else {
            AuthResource.Error(authResult.exceptionOrNull()?.toString() ?: "Ha ocurrido un error al intentar ingresar")
        }
    }

    override suspend fun signOut(): Boolean {
        dataStoreManager.storeValue(PreferencesKeys.TOKEN, "")
        return true
    }

    override suspend fun saveUserProfileData(
        userDTO: UserDTO,
        userId: Int
    ): AuthResource<Boolean> {
        val response = apiDataSource.saveUserProfileData(
            userId = userId,
            userDTO = userDTO
        )
        val responseData = response.getOrNull()
        val userData = responseData?.data
        return if (response.isSuccess && userData != null) {
            val user = UserEntity(userData)
            with(dataStoreManager) {
                storeValue(PreferencesKeys.ROLE, user.role)
                storeValue(PreferencesKeys.USERNAME, user.username)
                storeValue(PreferencesKeys.FULL_NAME, user.fullName)
                storeValue(PreferencesKeys.GENDER, user.gender)
                storeValue(PreferencesKeys.PHOTO, user.photo)
                storeValue(PreferencesKeys.PHONE, user.phone)
            }
            AuthResource.Success(true)
        } else {
            AuthResource.Error("Ha ocurrido un error al intentar crear el usuario")
        }
    }
}
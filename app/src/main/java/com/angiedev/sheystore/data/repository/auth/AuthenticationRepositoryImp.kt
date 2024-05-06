package com.angiedev.sheystore.data.repository.auth

import com.angiedev.sheystore.data.datasource.local.DataStoreManager
import com.angiedev.sheystore.data.datasource.remote.ApiDataSource
import com.angiedev.sheystore.data.entities.UserEntity
import com.angiedev.sheystore.data.model.remote.request.CreateUserFields
import com.angiedev.sheystore.data.model.remote.response.DocumentResponse
import com.angiedev.sheystore.data.model.remote.response.StringResponse
import com.angiedev.sheystore.data.model.remote.response.UserResponse
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.data.util.parseArray
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.angiedev.sheystore.ui.utils.extension.getHours
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth,
    private val apiDataSource: ApiDataSource
) : IAuthenticationRepository {

    override suspend fun isAuthenticate(currentTime: Long): Boolean {
        var isAuthenticated = ""
        var timeSession = -1L
        dataStoreManager.readValue(PreferencesKeys.TOKEN) {
            isAuthenticated = this
        }
        dataStoreManager.readValue(PreferencesKeys.TIME_SESSION) {
            timeSession = this
        }
        val hoursSession = currentTime.getHours(timeSession)
        return isAuthenticated.isNotBlank() && hoursSession < 1
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResource<Boolean> {
        val authResult = apiDataSource.createAccount(email, password)
        val responseData = authResult.getOrNull()

        return if (authResult.isSuccess && responseData != null) {
            dataStoreManager.storeValue(PreferencesKeys.TOKEN, responseData.idToken.toString())
            dataStoreManager.storeValue(PreferencesKeys.EMAIL, responseData.email.toString())
            AuthResource.Success(true)
        } else {
            AuthResource.Error(authResult.exceptionOrNull()?.toString() ?: "Ha ocurrido un error al intentar crear la cuenta")
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
        timeSession: Long
    ): AuthResource<Boolean> {
        val authResult = apiDataSource.signInWithPassword(email, password)
        val responseData = authResult.getOrNull()

        return if (authResult.isSuccess && responseData != null) {
            dataStoreManager.storeValue(PreferencesKeys.TOKEN, responseData.idToken.toString())
            dataStoreManager.storeValue(PreferencesKeys.EMAIL, responseData.email.toString())

            val createdUserResponse = apiDataSource.fetchUserByDocumentId(responseData.email.orEmpty()).getOrNull()
            if (createdUserResponse != null) {
                val createdUser = UserEntity(parseArray<DocumentResponse<UserResponse>>(Gson().toJson(createdUserResponse)).fields)
                with(dataStoreManager) {
                    storeValue(PreferencesKeys.ROLE, createdUser.role)
                    storeValue(PreferencesKeys.USERNAME, createdUser.username)
                    storeValue(PreferencesKeys.FULL_NAME, createdUser.fullName)
                    storeValue(PreferencesKeys.PHOTO, createdUser.photo)
                    storeValue(PreferencesKeys.PHONE, createdUser.phone)
                }
            }

            AuthResource.Success(true)
        } else {
            AuthResource.Error(authResult.exceptionOrNull()?.toString() ?: "Ha ocurrido un error al intentar ingresar")
        }
    }



    override suspend fun resetPassword(email: String): AuthResource<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResource.Success(Unit)
        } catch(e: Exception) {
            AuthResource.Error(e.message ?: "Error al restablecer la contraseña")
        }
    }

    override suspend fun signOut() {
        dataStoreManager.storeValue(PreferencesKeys.TOKEN, "")
    }

    override suspend fun saveUserProfileData(
        createUserFields: CreateUserFields,
        email: String
    ): AuthResource<Boolean> {
        val response = apiDataSource.saveUserProfileData(
            email =  email,
            createUserFields = createUserFields
        )
        return if (response.isSuccess) {
            val createdUser = UserEntity(parseArray<DocumentResponse<UserResponse>>(Gson().toJson(response)).fields)
            with(dataStoreManager) {
                storeValue(PreferencesKeys.ROLE, createdUser.role)
                storeValue(PreferencesKeys.USERNAME, createdUser.username)
                storeValue(PreferencesKeys.FULL_NAME, createdUser.fullName)
                storeValue(PreferencesKeys.PHOTO, createdUser.photo)
                storeValue(PreferencesKeys.PHONE, createdUser.phone)
            }
            AuthResource.Success(true)
        } else {
            AuthResource.Error("Ha ocurrido un error al intentar crear el usuario")
        }
    }
}
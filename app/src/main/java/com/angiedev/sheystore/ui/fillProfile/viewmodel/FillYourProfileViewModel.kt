package com.angiedev.sheystore.ui.fillProfile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.request.CreateUserFields
import com.angiedev.sheystore.data.model.remote.response.StringResponse
import com.angiedev.sheystore.data.repository.auth.AuthenticationRepositoryImp
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.data.repository.datastorage.IDataStoragePreferences
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FillYourProfileViewModel @Inject constructor(
    private val dataStoragePreferences: IDataStoragePreferences,
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {

    private val _userSavedSuccessfully = MutableLiveData<AuthResource<Boolean>>()
    val userSavedSuccessfully get() = _userSavedSuccessfully

    fun getEmail() = runBlocking {
        dataStoragePreferences.readValue(PreferencesKeys.EMAIL).orEmpty()
    }

    fun saveUserProfileData(
        fullName: String,
        username: String,
        phone: String,
        gender: String,
        roleId: String,
        email: String,
        photo: String
    ) {
        runBlocking(Dispatchers.IO) {
            val response = authenticationRepository.saveUserProfileData(
                createUserFields = CreateUserFields(
                    fullName = StringResponse(fullName),
                    username = StringResponse(username),
                    phone = StringResponse(phone),
                    gender = StringResponse(gender),
                    role = StringResponse(roleId),
                    photo = StringResponse(photo)
                ),
                email = email
            )

            _userSavedSuccessfully.postValue(response)
        }
    }
}
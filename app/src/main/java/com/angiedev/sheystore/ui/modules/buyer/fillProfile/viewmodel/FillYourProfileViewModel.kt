package com.angiedev.sheystore.ui.modules.buyer.fillProfile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.domain.entities.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FillYourProfileViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {

    private val _userSavedSuccessfully = MutableLiveData<AuthResource<Boolean>>()
    val userSavedSuccessfully get() = _userSavedSuccessfully
    fun saveUserProfileData(
        fullName: String,
        username: String,
        phone: String,
        gender: String,
        roleId: String,
        photo: String,
        userId: Int
    ) {
        runBlocking(Dispatchers.IO) {
            val response = authenticationRepository.saveUserProfileData(
                user = UserEntity(
                    id = 0,
                    fullName = fullName,
                    username = username,
                    phone = phone,
                    gender = gender,
                    role = roleId,
                    photo = photo
                ),
                userId = userId
            )

            _userSavedSuccessfully.postValue(response)
        }
    }
}
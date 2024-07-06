package com.angiedev.sheystore.ui.modules.admin.usermanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.data.repository.users.UsersRepository
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.domain.entities.user.UseSignUpEntity
import com.angiedev.sheystore.domain.entities.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val authenticationRepository: IAuthenticationRepository
): ViewModel() {

    private val _users = MutableLiveData<ApiResponse<List<UserEntity>>>()
    val users: LiveData<ApiResponse<List<UserEntity>>> = _users

    private val _userUpdated = MutableLiveData<ApiResponse<Boolean>>()
    val userUpdated: LiveData<ApiResponse<Boolean>> = _userUpdated

    private val _userCreated = MutableLiveData<AuthResource<UseSignUpEntity>>()
    val userCreated: LiveData<AuthResource<UseSignUpEntity>> = _userCreated

    private val _userDeleted = MutableLiveData<ApiResponse<Boolean>>()
    val userDeleted: LiveData<ApiResponse<Boolean>> = _userDeleted

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(usersRepository.getAllUsers())
        }
    }

    fun updateUser(userId: Int, userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _userUpdated.postValue(usersRepository.updateUser(userId, userEntity))
            getUsers()
        }
    }

    fun createUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authenticationRepository.createUserWithEmailAndPassword(email, password)
            _userCreated.postValue(response)
            getUsers()
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _userDeleted.postValue(usersRepository.deleteUser(userId))
            getUsers()
        }
    }

}
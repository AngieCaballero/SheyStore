package com.angiedev.sheystore.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated get() = _isAuthenticated

    private val _isAuthored = MutableLiveData(false)
    val isAuthored get() = _isAuthored

    fun isAuthored() {
        viewModelScope.launch {
            _isAuthored.postValue(authenticationRepository.isAuthenticate())
        }
    }

    private fun login(username: String, password: String) {
        // Make request to login
    }

    fun validateCredentials(
        usernameToValidate: String,
        passwordToValidate: String
    ) {
        when {
            usernameToValidate.isEmpty() && !passwordToValidate.validatePassword() -> {
                password.postValue("La contraseña debe tener un mínimo de 8 caracteres")
                username.postValue("Campo requerido")
            }
            usernameToValidate.isEmpty() -> {
                username.postValue("Campo requerido")
            }
            !passwordToValidate.validatePassword() -> {
                password.postValue("La contraseña debe tener un mínimo de 8 caracteres")
            }
            else -> {
                password.postValue("")
                username.postValue("")
                login(usernameToValidate, passwordToValidate)
            }
        }
    }
}
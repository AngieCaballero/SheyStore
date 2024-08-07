package com.angiedev.sheystore.ui.modules.login.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.domain.entities.user.SignInEntity
import com.angiedev.sheystore.domain.entities.user.UseSignUpEntity
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isAuthored = MutableLiveData<Boolean>()
    val isAuthored get() = _isAuthored

    private val _sigInWithEmailAndPassword = MutableLiveData<AuthResource<SignInEntity>>()
    val signInWithEmailAndPassword get() = _sigInWithEmailAndPassword

    private val _createUserWithEmailAndPassword = MutableLiveData<AuthResource<UseSignUpEntity>>()
    val createUserWithEmailAndPassword get() = _createUserWithEmailAndPassword

    private val _signOut = MutableLiveData(false)
    val signOut get() = _signOut

    fun resetSignOutState() {
        _signOut.postValue(false)
    }

    fun resetSignInState() {
        _sigInWithEmailAndPassword.postValue(AuthResource.Error(""))
    }

    fun resetSignUpState() {
        _createUserWithEmailAndPassword.postValue(AuthResource.Error(""))
    }

    fun isAuthored(currentTime: Long) {
        runBlocking(Dispatchers.IO) {
            _isAuthored.postValue(authenticationRepository.isAuthenticate(currentTime))
        }
    }

    private fun signInWithEmailAndPassword(username: String, password: String) {
        viewModelScope.launch {
            val response = authenticationRepository.signInWithEmailAndPassword(
                username, password
            )
            _sigInWithEmailAndPassword.postValue(response)
        }
    }

    private fun createUserWithEmailAndPassword(username: String, password: String) {
        viewModelScope.launch {
            val response = authenticationRepository.createUserWithEmailAndPassword(
                username, password
            )
            _createUserWithEmailAndPassword.postValue(response)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val response = authenticationRepository.signOut()
            _signOut.postValue(response)
        }
    }

    fun validateCredentials(
        usernameToValidate: String,
        passwordToValidate: String,
        isSignInUser: Boolean
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
                if (isSignInUser) {
                    signInWithEmailAndPassword(usernameToValidate, passwordToValidate)
                } else {
                    createUserWithEmailAndPassword(usernameToValidate, passwordToValidate)
                }
            }
        }
    }
}
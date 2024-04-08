package com.angiedev.sheystore.ui.login.viewmodel

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angiedev.sheystore.data.Util.AuthResource
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isAuthored = MutableLiveData(false)
    val isAuthored get() = _isAuthored

    private val _sigInWithEmailAndPassword = MutableLiveData<AuthResource<FirebaseUser?>>()
    val signInWithEmailAndPassword get() = _sigInWithEmailAndPassword

    private val _createUserWithEmailAndPassword = MutableLiveData<AuthResource<FirebaseUser?>>()
    val createUserWithEmailAndPassword get() = _createUserWithEmailAndPassword

    private val _signOut = MutableLiveData(false)
    val signOut get() = _signOut

    private val _handleSigInResult = MutableLiveData<AuthResource<GoogleSignInAccount>?>()
    val handleSignInResult get() = _handleSigInResult

    private val _sigInWithGoogleCredential = MutableLiveData<AuthResource<FirebaseUser>?>()
    val sigInWithGoogleCredential get() = _sigInWithGoogleCredential

    fun isAuthored() {
        viewModelScope.launch {
            _isAuthored.postValue(authenticationRepository.isAuthenticate())
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

    fun signInWithGoogleCredential(credential: AuthCredential) {
        viewModelScope.launch {
            val response = authenticationRepository.signInWithGoogleCredential(credential)
            _sigInWithGoogleCredential.postValue(response)
        }
    }

    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        authenticationRepository.signInWithGoogle(googleSignInLauncher)
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        val response = authenticationRepository.handleSignInResult(task)
        _handleSigInResult.postValue(response)
    }

    private fun sigOut() {
        viewModelScope.launch {
            authenticationRepository.signOut()
            _signOut.postValue(true)
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
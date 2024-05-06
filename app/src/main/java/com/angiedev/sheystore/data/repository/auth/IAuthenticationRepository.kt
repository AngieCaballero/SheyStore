package com.angiedev.sheystore.data.repository.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.angiedev.sheystore.data.model.remote.request.CreateUserFields
import com.angiedev.sheystore.data.model.remote.response.SignInResponse
import com.angiedev.sheystore.data.model.remote.response.SignUpResponse
import com.angiedev.sheystore.data.util.AuthResource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthenticationRepository {

    suspend fun isAuthenticate(currentTime: Long): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResource<Boolean>

    suspend fun signInWithEmailAndPassword(email: String, password: String, timeSession: Long): AuthResource<Boolean>

    suspend fun resetPassword(email: String): AuthResource<Unit>

    suspend fun signOut()

    suspend fun saveUserProfileData(createUserFields: CreateUserFields, email: String) : AuthResource<Boolean>
}
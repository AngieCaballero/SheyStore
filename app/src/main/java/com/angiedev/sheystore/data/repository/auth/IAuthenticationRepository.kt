package com.angiedev.sheystore.data.repository.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.angiedev.sheystore.data.model.AuthResource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthenticationRepository {

    suspend fun isAuthenticate(): Boolean

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResource<FirebaseUser?>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResource<FirebaseUser?>

    suspend fun resetPassword(email: String): AuthResource<Unit>

    suspend fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthResource<GoogleSignInAccount>?

    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthResource<FirebaseUser>?

    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>)
}
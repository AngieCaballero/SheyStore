package com.angiedev.sheystore.data.repository.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.angiedev.sheystore.core.di.manager.DataStoreManager
import com.angiedev.sheystore.data.model.AuthResource
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val signInClient: SignInClient
) : IAuthenticationRepository {

    override suspend fun isAuthenticate(): Boolean {
        var isAuthenticated = false
        dataStoreManager.readValue(PreferencesKeys.TOKEN) {
            isAuthenticated = isNotBlank()
        }
        return isAuthenticated
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResource<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            dataStoreManager.storeValue(PreferencesKeys.TOKEN, authResult.user?.getIdToken(true)?.await()?.token.toString())
            AuthResource.Success(authResult.user)
        } catch(e: Exception) {
            AuthResource.Error(e.message ?: "Error al crear el usuario")
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResource<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            dataStoreManager.storeValue(PreferencesKeys.TOKEN, authResult.user?.getIdToken(true)?.await()?.token.toString())
            AuthResource.Success(authResult.user)
        } catch(e: Exception) {
            AuthResource.Error(e.message ?: "Error al iniciar sesión")
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
        firebaseAuth.signOut()
        signInClient.signOut()
        dataStoreManager.storeValue(PreferencesKeys.TOKEN, "")
    }

    override fun getCurrentUser() = firebaseAuth.currentUser

    override fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthResource<GoogleSignInAccount>? {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthResource.Success(account)
        } catch (e: ApiException) {
            AuthResource.Error("Google sign-in failed.")
        }
    }

    override suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthResource<FirebaseUser>? {
        return try {
            val firebaseUser = firebaseAuth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                dataStoreManager.storeValue(PreferencesKeys.TOKEN, it.getIdToken(true).await()?.token.toString())
                AuthResource.Success(it)
            } ?: throw Exception("Sign in with Google failed.")
        } catch (e: Exception) {
            AuthResource.Error(e.message ?: "Sign in with Google failed.")
        }
    }

    override fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
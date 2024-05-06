package com.angiedev.sheystore.core.di.module

import android.content.Context
import com.angiedev.sheystore.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAuthModule {

    @Singleton
    @Provides
    fun provideGoogleSignInClientOptions(@ApplicationContext context: Context): GoogleSignInClient {
        val googleSignInOptions =  GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }

    @Singleton
    @Provides
    fun provideBeginSigInRequest(@ApplicationContext context: Context) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()
        ).build()

    @Provides
    @Singleton
    fun provideSingInClient(@ApplicationContext context: Context) = Identity.getSignInClient(context)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth
}

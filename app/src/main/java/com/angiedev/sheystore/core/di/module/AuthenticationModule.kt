package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.auth.AuthenticationRepositoryImp
import com.angiedev.sheystore.data.repository.auth.IAuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthenticationModule {

    @Binds
    abstract fun provideAuthenticationRepository(authenticationRepositoryImp: AuthenticationRepositoryImp) : IAuthenticationRepository

}
package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.users.UsersRepository
import com.angiedev.sheystore.data.repository.users.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserModule {

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UsersRepositoryImpl) : UsersRepository

}
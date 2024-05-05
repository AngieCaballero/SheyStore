package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.datastorage.DataStoragePreferencesImp
import com.angiedev.sheystore.data.repository.datastorage.IDataStoragePreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataPreferencesModule {

    @Binds
    abstract fun provideDataStoragePreferences(dataStoragePreferencesImp: DataStoragePreferencesImp) : IDataStoragePreferences
}
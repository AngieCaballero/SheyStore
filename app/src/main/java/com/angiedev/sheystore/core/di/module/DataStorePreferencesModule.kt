package com.angiedev.sheystore.core.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.angiedev.sheystore.core.di.manager.DataStoreManager
import com.angiedev.sheystore.ui.utils.constant.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {

    @Provides
    @Singleton
    fun providePreferenceDataStoreFactory(@ApplicationContext context: Context) = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        produceFile = { context.preferencesDataStoreFile(Constant.DATASTORE_PREFERENCE_NAME) }
    )

    @Singleton
    @Provides
    fun provideDataStoreManager(dataStore: DataStore<Preferences>) = DataStoreManager(dataStore)

}
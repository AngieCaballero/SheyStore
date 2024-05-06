package com.angiedev.sheystore.data.repository.datastorage

import androidx.datastore.preferences.core.Preferences
import com.angiedev.sheystore.data.datasource.local.DataStoreManager
import javax.inject.Inject

class DataStoragePreferencesImp @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : IDataStoragePreferences{

    override suspend fun <T> readValue(key: Preferences.Key<T>): T? {
        var data: T? = null
        dataStoreManager.readValue(key) {
            data = this
        }
        return data
    }

    override suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        dataStoreManager.storeValue(key, value)
    }
}
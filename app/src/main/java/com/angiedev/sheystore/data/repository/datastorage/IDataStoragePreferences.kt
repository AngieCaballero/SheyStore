package com.angiedev.sheystore.data.repository.datastorage

import androidx.datastore.preferences.core.Preferences

interface IDataStoragePreferences {
    suspend fun <T> readValue(key: Preferences.Key<T>) : T?

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T)
}
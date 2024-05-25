package com.angiedev.sheystore.ui.user.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.repository.datastorage.IDataStoragePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val dataStoragePreferences: IDataStoragePreferences
) : ViewModel() {
    fun <T> readValue(key: Preferences.Key<T>) = runBlocking(Dispatchers.IO) {
        dataStoragePreferences.readValue(key)
    }
}
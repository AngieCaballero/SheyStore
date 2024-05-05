package com.angiedev.sheystore.ui.fillProfile.viewmodel

import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.repository.datastorage.IDataStoragePreferences
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FillYourProfileViewModel @Inject constructor(
    private val dataStoragePreferences: IDataStoragePreferences
): ViewModel() {

    fun getEmail() = runBlocking {
        dataStoragePreferences.readValue(PreferencesKeys.EMAIL).orEmpty()
    }
}
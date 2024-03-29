package com.angiedev.sheystore.data.repository.auth

import com.angiedev.sheystore.core.di.manager.DataStoreManager
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : IAuthenticationRepository {

    override suspend fun isAuthenticate(): Boolean {
        var isAuthenticated = false
        dataStoreManager.readValue(PreferencesKeys.KEY) {
            isAuthenticated = isNotBlank()
        }
        return isAuthenticated
    }
}
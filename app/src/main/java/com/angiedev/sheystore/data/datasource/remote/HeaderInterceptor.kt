package com.angiedev.sheystore.data.datasource.remote

import com.angiedev.sheystore.data.datasource.local.DataStoreManager
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class HeaderInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = ""
        runBlocking(Dispatchers.IO) {
            dataStoreManager.readValue(PreferencesKeys.TOKEN) {
                accessToken = this
            }
        }
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")

        // When host is identitytoolkit.googleapis.com don't need Bearer Token
        val requestBuilderWithHeader = if (chain.request().url.host.contains("identitytoolkit.googleapis.com")) {
            requestBuilder.build()
        } else {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken").build()
        }

        return chain.proceed(requestBuilderWithHeader)
    }

}
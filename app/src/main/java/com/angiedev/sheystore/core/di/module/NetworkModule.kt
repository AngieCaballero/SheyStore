package com.angiedev.sheystore.core.di.module

import com.kmc.networking.NetworkingBaseUrl
import com.kmc.networking.NetworkingOkHttp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @NetworkingBaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl(): URL = URL("https://firestore.googleapis.com/v1/projects/sheystore-d7393/databases/(default)/documents/")

    @NetworkingOkHttp
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}

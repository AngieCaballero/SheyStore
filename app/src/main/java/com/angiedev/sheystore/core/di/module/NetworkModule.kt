package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.datasource.remote.HeaderInterceptor
import com.angiedev.sheystore.data.datasource.local.DataStoreManager
import com.angiedev.sheystore.data.datasource.remote.ApiDownload
import com.google.gson.GsonBuilder
import com.kmc.networking.NetworkingBaseUrl
import com.kmc.networking.NetworkingOkHttp
import com.kmc.networking.NetworkingRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(dataStoreManager: DataStoreManager) = HeaderInterceptor(dataStoreManager)

    @NetworkingBaseUrl
    @Singleton
    @Provides
    fun provideBaseUrl(): URL = URL("http://192.168.2.102:3001/")

    @Singleton
    @Provides
    fun provideRetrofit(
        @NetworkingBaseUrl baseUrl: URL,
        @NetworkingOkHttp okHttpClient: OkHttpClient
    ): ApiDownload {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(ApiDownload::class.java)
    }



    @NetworkingOkHttp
    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}

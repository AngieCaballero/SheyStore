package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.model.remote.CollectionResponse
import com.angiedev.sheystore.model.remote.DocumentResponse
import com.angiedev.sheystore.model.remote.ProductResponse
import com.kmc.networking.entity.HttpMethod
import com.kmc.networking.interfaces.NetworkCaller
import com.kmc.networking.interfaces.request
import com.kmc.networking.interfaces.safeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkCaller {

    private val service = networkingService(context)

    suspend fun fetchProducts(
        userToken: String
    ) = service.safeRequest<CollectionResponse<ProductResponse>>(
        endpoint = "product",
        method = HttpMethod.Get,
        withHeaders = mapOf(
            "Content-type" to "application/json; charset=UTF-8",
            "Authorization" to "Bearer $userToken"
        )
    )

    suspend fun fetchProductById(
        productId: String,
        userToken: String
    ) = service.safeRequest<DocumentResponse<ProductResponse>>(
        endpoint = "product/$productId",
        method = HttpMethod.Get,
        withHeaders = mapOf(
            "Content-type" to "application/json; charset=UTF-8",
            "Authorization" to "Bearer $userToken"
        )
    )
}
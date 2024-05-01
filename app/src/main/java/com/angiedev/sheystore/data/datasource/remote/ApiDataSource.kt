package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.CategoryResponse
import com.angiedev.sheystore.data.model.remote.CollectionResponse
import com.angiedev.sheystore.data.model.remote.DocumentResponse
import com.angiedev.sheystore.data.model.remote.ProductDetailsResponse
import com.angiedev.sheystore.data.model.remote.ProductResponse
import com.angiedev.sheystore.data.model.remote.SpecialsOffersResponse
import com.kmc.networking.HttpMethod
import com.kmc.networking.NetworkCaller
import com.kmc.networking.safeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkCaller {

    private val service = networkingService(context)

    suspend fun getSpecialsOffers() = service.safeRequest(endpoint = "specials_offers/")
        .withMethod(HttpMethod.GET)
        .execute<CollectionResponse<SpecialsOffersResponse>>()

    suspend fun getCategories() = service.safeRequest(endpoint = "category/")
        .withMethod(HttpMethod.GET)
        .execute<CollectionResponse<CategoryResponse>>()

    suspend fun getProducts() = service.safeRequest(endpoint = "product/")
        .withMethod(HttpMethod.GET)
        .execute<CollectionResponse<ProductResponse>>()

    suspend fun getProductDetails(productId: String) = service.safeRequest(endpoint = "product_details/$productId/")
        .withMethod(HttpMethod.GET)
        .execute<DocumentResponse<ProductDetailsResponse>>()
}
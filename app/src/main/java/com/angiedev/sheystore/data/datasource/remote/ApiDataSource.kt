package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.CategoryResponse
import com.angiedev.sheystore.data.model.remote.CollectionResponse
import com.angiedev.sheystore.data.model.remote.DocumentResponse
import com.angiedev.sheystore.data.model.remote.ProductDetailsResponse
import com.angiedev.sheystore.data.model.remote.ProductResponse
import com.angiedev.sheystore.data.model.remote.SpecialsOffersResponse
import com.kmc.networking.entity.HttpMethod
import com.kmc.networking.interfaces.NetworkCaller
import com.kmc.networking.interfaces.safeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkCaller {

    private val service = networkingService(context)

    suspend fun getSpecialsOffers() = service.safeRequest<CollectionResponse<SpecialsOffersResponse>>(
        endpoint = "specials_offers/",
        method = HttpMethod.Get
    )

    suspend fun getCategories() = service.safeRequest<CollectionResponse<CategoryResponse>>(
        endpoint = "category/",
        method = HttpMethod.Get
    )

    suspend fun getProducts() = service.safeRequest<CollectionResponse<ProductResponse>>(
        endpoint = "product/",
        method = HttpMethod.Get
    )

    suspend fun getProductDetails(productId: String) = service.safeRequest<DocumentResponse<ProductDetailsResponse>>(
        endpoint = "product_details/$productId/",
        method = HttpMethod.Get
    )
}
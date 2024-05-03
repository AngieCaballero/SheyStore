package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.response.CategoryResponse
import com.angiedev.sheystore.data.model.remote.response.CollectionResponse
import com.angiedev.sheystore.data.model.remote.request.CreateUserFields
import com.angiedev.sheystore.data.model.remote.response.DocumentResponse
import com.angiedev.sheystore.data.model.remote.response.ProductDetailsResponse
import com.angiedev.sheystore.data.model.remote.response.ProductResponse
import com.angiedev.sheystore.data.model.remote.response.SignUpResponse
import com.angiedev.sheystore.data.model.remote.response.SpecialsOffersResponse
import com.angiedev.sheystore.data.model.remote.response.StringResponse
import com.angiedev.sheystore.data.model.remote.response.UserResponse
import com.kmc.networking.HttpMethod
import com.kmc.networking.NetworkCaller
import com.kmc.networking.safeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkCaller {

    private val service = networkingService(context)

    /*Log in request*/
    suspend fun createAccount(email: String, password: String) = service.safeRequest(
        endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyAFv5UYUlZuPZxQJB9XKkVm4mwcjHmUx64"
    )
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "email" to email,
                "password" to password,
                "returnSecureToken" to true
            )
        )
        .execute<SignUpResponse>()

    suspend fun createUser(email: String, role: String) = service.safeRequest(endpoint = "users?documentId=${email}")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "fields" to CreateUserFields(
                    lastname = StringResponse(""),
                    name = StringResponse(""),
                    role = StringResponse(role),
                    username = StringResponse("")
                )
            )
        )
        .execute<DocumentResponse<UserResponse>>()

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
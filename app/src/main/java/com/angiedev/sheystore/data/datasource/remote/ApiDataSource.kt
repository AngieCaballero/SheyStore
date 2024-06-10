package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.response.CartValueResponse
import com.angiedev.sheystore.data.model.remote.response.DocumentCartResponse
import com.angiedev.sheystore.data.model.remote.response.DocumentShippingAddressResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressValueResponse
import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.specialsOffers.SpecialOfferResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.SignInResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.SignUpResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserResponseDTO
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
        endpoint = "auth/register/"
    )
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "email" to email,
                "password" to password
            )
        )
        .execute<SignUpResponseDTO>()

    suspend fun signInWithPassword(email: String, password: String) = service.safeRequest(
        endpoint = "auth/login/"
    )
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "email" to email,
                "password" to password,
                "returnSecureToken" to true
            )
        )
        .execute<SignInResponseDTO>()

    suspend fun saveUserProfileData(userId: Int, userDTO: UserDTO) = service.safeRequest(endpoint = "users/${userId}")
        .withMethod(HttpMethod.PATCH)
        .withBody(
            mapOf(
                "full_name" to userDTO.fullName,
                "username" to userDTO.username,
                "role" to userDTO.role,
                "phone" to userDTO.phone,
                "photo" to userDTO.photo,
                "gender" to userDTO.gender
            )
        )
        .execute<UserResponseDTO>()


    suspend fun getSpecialsOffers() = service.safeRequest(endpoint = "specials-offers/")
        .withMethod(HttpMethod.GET)
        .execute<SpecialOfferResponseDTO>()

    suspend fun getCategories() = service.safeRequest(endpoint = "category/")
        .withMethod(HttpMethod.GET)
        .execute<CategoryResponseDTO>()

    suspend fun getProducts() = service.safeRequest(endpoint = "product/")
        .withMethod(HttpMethod.GET)
        .execute<ProductResponseDTO>()


    suspend fun getCartItems(documentId: String) = service.safeRequest(endpoint = "cart/$documentId/")
        .withMethod(HttpMethod.GET)
        .execute<DocumentCartResponse>()

    suspend fun patchCartItems(documentId: String, body: CartValueResponse) = service.safeRequest(endpoint = "cart/$documentId/")
        .withMethod(HttpMethod.PATCH)
        .withBody(
            mapOf(
                "fields" to body
            )
        )
        .execute<DocumentCartResponse>()

    suspend fun getShippingAddress(documentId: String) = service.safeRequest(endpoint = "shipping_address/$documentId/")
        .withMethod(HttpMethod.GET)
        .execute<DocumentShippingAddressResponse>()

    suspend fun patchShippingAddress(documentId: String, body: ShippingAddressValueResponse) = service.safeRequest(endpoint = "shipping_address/$documentId/")
        .withMethod(HttpMethod.PATCH)
        .withBody(
            mapOf(
                "fields" to body
            )
        )
        .execute<DocumentShippingAddressResponse>()
}
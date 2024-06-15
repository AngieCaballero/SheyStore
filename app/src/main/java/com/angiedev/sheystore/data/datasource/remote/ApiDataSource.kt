package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.request.order.CreateOrderDTO
import com.angiedev.sheystore.data.model.remote.request.paymentMethod.CreatePaymentMethodDTO
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartItemDTO
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.order.OrderResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.order.OrderResponseListDTO
import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodResponseListDTO
import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress.ShippingAddressResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress.ShippingAddressResponseListDTO
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

    suspend fun saveUserProfileData(userId: Int, userDTO: UserDTO) =
        service.safeRequest(endpoint = "users/${userId}")
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


    suspend fun getCartItems(userId: Int) = service.safeRequest(endpoint = "cart/$userId/")
        .withMethod(HttpMethod.GET)
        .execute<CartResponseDTO>()

    suspend fun addProductToCart(userId: Int, createCartItemDTO: CreateCartItemDTO) =
        service.safeRequest(endpoint = "cart/$userId/")
            .withMethod(HttpMethod.PUT)
            .withBody(
                mapOf(
                    "product_id" to createCartItemDTO.productId,
                    "quantity" to createCartItemDTO.quantity,
                    "total_price" to createCartItemDTO.totalPrice,
                    "color" to createCartItemDTO.color
                )
            )
            .execute<CartResponseDTO>()

    suspend fun removeProductFromCart(userId: Int, cartItemId: Int) =
        service.safeRequest(endpoint = "cart/$userId/$cartItemId/")
            .withMethod(HttpMethod.DELETE)
            .execute<CartItemDTO>()

    suspend fun getShippingAddress(userId: Int) =
        service.safeRequest(endpoint = "shipping-address/user/${userId}/")
            .withMethod(HttpMethod.GET)
            .execute<ShippingAddressResponseListDTO>()

    suspend fun updateShippingAddress(
        userId: Int,
        shippingAddressId: Int,
        updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO
    ) =
        service.safeRequest(endpoint = "shipping-address/user/${userId}/shipping-address/${shippingAddressId}/")
            .withMethod(HttpMethod.PUT)
            .withBody(
                mapOf(
                    "name" to updateOrCreateShippingAddressDTO.name,
                    "details" to updateOrCreateShippingAddressDTO.details,
                    "default" to updateOrCreateShippingAddressDTO.default
                )
            )
            .execute<ShippingAddressResponseDTO>()

    suspend fun createShippingAddress(
        userId: Int,
        updateOrCreateShippingAddressDTO: UpdateOrCreateShippingAddressDTO
    ) = service.safeRequest(endpoint = "shipping-address/${userId}/")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "name" to updateOrCreateShippingAddressDTO.name,
                "details" to updateOrCreateShippingAddressDTO.details,
                "default" to updateOrCreateShippingAddressDTO.default
            )
        )
        .execute<ShippingAddressResponseDTO>()

    suspend fun createPaymentMethod(
        userId: Int,
        createPaymentMethodDTO: CreatePaymentMethodDTO
    ) = service.safeRequest(endpoint = "payment-method/user/${userId}/")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "card_name" to createPaymentMethodDTO.cardName,
                "card_number" to createPaymentMethodDTO.cardNumber,
                "expired_at" to createPaymentMethodDTO.expiredAt,
                "cvc_number" to createPaymentMethodDTO.cvcNumber
            )
        )
        .execute<PaymentMethodResponseDTO>()

    suspend fun getPaymentMethod(userId: Int) =
        service.safeRequest(endpoint = "payment-method/user/${userId}/")
            .withMethod(HttpMethod.GET)
            .execute<PaymentMethodResponseListDTO>()

    suspend fun createOrder(userId: Int, createOrderDTO: CreateOrderDTO) = service.safeRequest(endpoint = "order/user/${userId}/")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "orderStatus" to createOrderDTO.orderStatus,
                "cart_id" to createOrderDTO.cartId
            )
        )
        .execute<OrderResponseDTO>()

    suspend fun getOrders(userId: Int, orderStatus: String) = service.safeRequest(endpoint = "order/user/${userId}/status/${orderStatus}/")
        .withMethod(HttpMethod.GET)
        .execute<OrderResponseListDTO>()
}
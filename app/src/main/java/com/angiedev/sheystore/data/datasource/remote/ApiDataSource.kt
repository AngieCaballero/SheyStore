package com.angiedev.sheystore.data.datasource.remote

import android.content.Context
import com.angiedev.sheystore.data.model.remote.request.cart.CreateCartItemDTO
import com.angiedev.sheystore.data.model.remote.request.order.CreateOrderDTO
import com.angiedev.sheystore.data.model.remote.request.paymentMethod.CreatePaymentMethodDTO
import com.angiedev.sheystore.data.model.remote.request.review.CreateReviewDTO
import com.angiedev.sheystore.data.model.remote.request.shippingAddress.UpdateOrCreateShippingAddressDTO
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartItemDTO
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.order.OrderResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.order.OrderResponseListDTO
import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.paymentMethod.PaymentMethodResponseListDTO
import com.angiedev.sheystore.data.model.remote.response.dto.product.ProductResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.review.ReviewResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress.ShippingAddressResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress.ShippingAddressResponseListDTO
import com.angiedev.sheystore.data.model.remote.response.dto.specialsOffers.SpecialOfferResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.SignInResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.SignUpResponseDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserDTO
import com.angiedev.sheystore.data.model.remote.response.dto.user.UserResponseDTO
import com.angiedev.sheystore.domain.entities.product.ProductEntity
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

    /* Buyer */

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

    suspend fun getOrders(userId: Int, orderStatus: Int) = service.safeRequest(endpoint = "order/user/${userId}/status/${orderStatus}/")
        .withMethod(HttpMethod.GET)
        .execute<OrderResponseListDTO>()

    suspend fun sendReview(userId: Int, productId: Int, createReviewDTO: CreateReviewDTO) = service.safeRequest(endpoint = "review/user/$userId/product/$productId/")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "comment" to createReviewDTO.comment,
                "rating" to createReviewDTO.rating,
                "image" to createReviewDTO.image.orEmpty()
            )
        )
        .execute<ReviewResponseDTO>()


    /* Seller */

    suspend fun getProducts(userId: Int) = service.safeRequest(endpoint = "product/user/${userId}")
        .withMethod(HttpMethod.GET)
        .execute<ProductResponseDTO>()

    suspend fun saveProduct(userId: Int, product: ProductEntity) = service.safeRequest(endpoint = "product/user/${userId}")
        .withMethod(HttpMethod.POST)
        .withBody(
            mapOf(
                "name" to product.name,
                "description" to product.description,
                "price" to product.price,
                "category_id" to product.category.id,
                "colors" to product.colors,
                "discount" to product.discount,
                "quantity" to product.quantity,
                "image" to product.image,
                "presentation_images" to product.presentationImages,
                "rate" to product.rate,
                )
        )
        .execute<ProductResponseDTO>()

    suspend fun updateProduct(product: ProductEntity) = service.safeRequest(endpoint = "product/${product.id}")
        .withMethod(HttpMethod.PUT)
        .withBody(
            mapOf(
                "name" to product.name,
                "description" to product.description,
                "price" to product.price,
                "category" to product.category.id,
                "colors" to product.colors,
                "presentation_images" to product.presentationImages,
                "rate" to product.rate,

                )
        )
        .execute<Boolean>()

    suspend fun deleteProduct(productId: Int) = service.safeRequest(endpoint = "product/${productId}")
        .withMethod(HttpMethod.DELETE)
        .execute<ProductEntity>()
}
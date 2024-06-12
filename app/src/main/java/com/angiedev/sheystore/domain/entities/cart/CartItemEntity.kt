package com.angiedev.sheystore.domain.entities.cart

import android.os.Parcelable
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.dto.cart.CartItemDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItemEntity(
    val id: Int,
    val quantity: Int,
    val totalPrice: Double,
    val productId: Int,
    val color: String,
    val product: ProductEntity
) : Parcelable {
    constructor(cartItemDTO: CartItemDTO?) : this(
        id = cartItemDTO?.id ?: 0,
        quantity = cartItemDTO?.quantity ?: 0,
        totalPrice = cartItemDTO?.totalPrice ?: 0.0,
        productId = cartItemDTO?.productId ?: 0,
        color = cartItemDTO?.color.orEmpty(),
        product = ProductEntity(cartItemDTO?.product)
    )
}

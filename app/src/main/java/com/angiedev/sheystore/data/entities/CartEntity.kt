package com.angiedev.sheystore.data.entities

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.CartArrayValuesResponse
import com.angiedev.sheystore.data.model.remote.response.CartMapResponse
import com.angiedev.sheystore.data.model.remote.response.CartMapValueResponse
import com.angiedev.sheystore.data.model.remote.response.CartResponse
import com.angiedev.sheystore.data.model.remote.response.CartValueResponse
import com.angiedev.sheystore.data.model.remote.response.CartValuesResponse
import com.angiedev.sheystore.data.model.remote.response.StringResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartEntity(
    val id: String,
    val color: String,
    val image: String,
    val name: String,
    val price: String,
    val quantity: String,
    val totalPrice: String
) : Parcelable {

    fun toCartResponse() = CartResponse(
        id = StringResponse(id),
        color = StringResponse(color),
        image = StringResponse(image),
        name = StringResponse(name),
        quantity = StringResponse(quantity),
        price = StringResponse(price),
        totalPrice = StringResponse(totalPrice)
    )

    constructor(cartResponse: CartResponse?) : this(
        id = cartResponse?.id?.stringValue.orEmpty(),
        color = cartResponse?.color?.stringValue ?: "#FFFFFF",
        image = cartResponse?.image?.stringValue.orEmpty(),
        name = cartResponse?.name?.stringValue.orEmpty(),
        quantity = cartResponse?.quantity?.stringValue.orEmpty(),
        price = cartResponse?.price?.stringValue.orEmpty(),
        totalPrice = cartResponse?.totalPrice?.stringValue.orEmpty()
    )

    constructor(productDetailsEntity: ProductDetailsEntity?, totalPrice: String, quantity: Int) : this (
        id = productDetailsEntity?.id.orEmpty(),
        color = "#FFFFFF",
        image = productDetailsEntity?.images?.firstOrNull().orEmpty(),
        name = productDetailsEntity?.name.orEmpty(),
        quantity = quantity.toString(),
        price = productDetailsEntity?.price.orEmpty(),
        totalPrice = totalPrice
    )
}

fun List<CartEntity>.toCartValueResponse() = CartValueResponse(
    value = CartArrayValuesResponse(
        values = CartValuesResponse(
            values = map {
                CartMapResponse(
                    values = CartMapValueResponse(
                        it.toCartResponse()
                    )
                )
            }
        )
    )
)
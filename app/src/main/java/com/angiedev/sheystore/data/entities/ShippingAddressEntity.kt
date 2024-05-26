package com.angiedev.sheystore.data.entities

import android.os.Parcelable
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressArrayValuesResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressMapResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressMapValueResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressValueResponse
import com.angiedev.sheystore.data.model.remote.response.ShippingAddressValuesResponse
import com.angiedev.sheystore.data.model.remote.response.StringResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingAddressEntity(
    val name: String,
    val details: String,
    var default: Boolean
) : Parcelable {

    fun toShippingAddressResponse() = ShippingAddressResponse(
        name = StringResponse(name),
        default = StringResponse(default.toString()),
        details = StringResponse(details)
    )

    constructor(shippingAddress: ShippingAddressResponse?) : this(
        name = shippingAddress?.name?.stringValue.orEmpty(),
        details = shippingAddress?.details?.stringValue.orEmpty(),
        default = shippingAddress?.default?.stringValue?.toBooleanStrictOrNull() ?: false
    )
}

fun List<ShippingAddressEntity>.toShippingAddressValueResponse() = ShippingAddressValueResponse(
    shippingAddress = ShippingAddressArrayValuesResponse(
        values = ShippingAddressValuesResponse(
            values = map {
                ShippingAddressMapResponse(
                    values = ShippingAddressMapValueResponse(
                        it.toShippingAddressResponse()
                    )
                )
            }
        )
    )
)
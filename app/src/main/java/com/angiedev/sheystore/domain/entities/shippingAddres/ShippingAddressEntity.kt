package com.angiedev.sheystore.domain.entities.shippingAddres

import com.angiedev.sheystore.data.model.remote.response.dto.shppingAddress.ShippingAddressDTO

data class ShippingAddressEntity(
    val id: Int,
    val name: String,
    val details: String,
    var default: Boolean
) {
    constructor(shippingAddressDTO: ShippingAddressDTO?) : this(
        id = shippingAddressDTO?.id ?: 0,
        name = shippingAddressDTO?.name.orEmpty(),
        details = shippingAddressDTO?.details.orEmpty(),
        default = shippingAddressDTO?.default ?: false
    )
}

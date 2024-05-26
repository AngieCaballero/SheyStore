package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingAddressResponse(
    @SerializedName("name") val name: StringResponse?,
    @SerializedName("details") val details: StringResponse?,
    @SerializedName("default") val default: StringResponse?
) : Parcelable

@Parcelize
data class DocumentShippingAddressResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("fields") val fields: ShippingAddressValueResponse?,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("updateTime") val updateTime: String
) : Parcelable

@Parcelize
data class ShippingAddressValueResponse(
    @SerializedName("shipping_address") val shippingAddress: ShippingAddressArrayValuesResponse?
) : Parcelable {
    fun toShippingAddressEntityList() = shippingAddress?.values?.values?.map {
        ShippingAddressEntity(it.values.fields)
    }.orEmpty()
}

@Parcelize
data class ShippingAddressArrayValuesResponse(
    @SerializedName("arrayValue") val values: ShippingAddressValuesResponse
) : Parcelable

@Parcelize
data class ShippingAddressValuesResponse(
    @SerializedName("values") val values: List<ShippingAddressMapResponse>
) : Parcelable

@Parcelize
data class ShippingAddressMapResponse(
    @SerializedName("mapValue") val values: ShippingAddressMapValueResponse
) : Parcelable

@Parcelize
data class ShippingAddressMapValueResponse(
    @SerializedName("fields") val fields: ShippingAddressResponse?
) : Parcelable

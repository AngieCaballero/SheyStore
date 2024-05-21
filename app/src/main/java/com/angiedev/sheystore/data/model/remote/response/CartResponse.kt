package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartResponse(
    @SerializedName("id") val id: StringResponse?,
    @SerializedName("color") val color: StringResponse?,
    @SerializedName("image") val image: StringResponse?,
    @SerializedName("name") val name: StringResponse?,
    @SerializedName("price") val price: StringResponse?,
    @SerializedName("quantity") val quantity: StringResponse?,
    @SerializedName("totalPrice") val totalPrice: StringResponse?
) : Parcelable

@Parcelize
data class DocumentCartResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("fields") val fields: CartValueResponse?,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("updateTime") val updateTime: String
) : Parcelable

@Parcelize
data class CartValueResponse(
    @SerializedName("cart") val value: CartArrayValuesResponse?
) : Parcelable

@Parcelize
data class CartArrayValuesResponse(
    @SerializedName("arrayValue") val values: CartValuesResponse
) : Parcelable

@Parcelize
data class CartValuesResponse(
    @SerializedName("values") val values: List<CartMapResponse>
) : Parcelable

@Parcelize
data class CartMapResponse(
    @SerializedName("mapValue") val values: CartMapValueResponse
) : Parcelable
@Parcelize
data class CartMapValueResponse(
    @SerializedName("fields") val fields: CartResponse?
) : Parcelable
package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@JsonClass(generateAdapter = true)
data class CollectionResponse<T> (
    @SerializedName("documents")
    val documents: List<DocumentResponse<T>> = emptyList()
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class DocumentResponse<T> (
    @SerializedName("name")
    val name: String = "",
    @SerializedName("fields")
    val fields: @RawValue T? = null,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("updateTime")
    val updateTime: String = ""
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class IntegerResponse(
    @SerializedName("integerValue")
    val integerValue: String = ""
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class StringResponse(
    @SerializedName("stringValue")
    val stringValue: String = ""
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class DoubleResponse(
    @SerializedName("doubleValue")
    val doubleValue: Double = 0.0
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ArrayResponse(
    @SerializedName("arrayValue")
    val arrayValue: ArrayValuesResponse
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class ArrayValuesResponse(
    @SerializedName("values")
    val values: ArrayList<StringResponse>
) : Parcelable
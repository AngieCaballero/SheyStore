package com.angiedev.sheystore.data.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CollectionResponse<T> (
    @SerializedName("documents")
    val documents: List<DocumentResponse<T>> = emptyList()
) : Parcelable

@Parcelize
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
data class IntegerResponse(
    @SerializedName("integerValue")
    val integerValue: String = ""
) : Parcelable

@Parcelize
data class StringResponse(
    @SerializedName("stringValue")
    val stringValue: String = ""
) : Parcelable

@Parcelize
data class DoubleResponse(
    @SerializedName("doubleValue")
    val doubleValue: Double = 0.0
) : Parcelable

@Parcelize
data class ArrayResponse<T>(
    @SerializedName("arrayValue")
    val arrayValue: ArrayValuesResponse<T>
) : Parcelable

@Parcelize
data class ArrayValuesResponse<T>(
    @SerializedName("values")
    val values: @RawValue ArrayList<T>
) : Parcelable

@Parcelize
data class MapValueResponse<T>(
    @SerializedName("mapValue") val mapValue: DocumentMapValueResponse<T>
) : Parcelable

@Parcelize
data class DocumentMapValueResponse<T>(
    @SerializedName("fields") val fields: @RawValue T?
) : Parcelable
package com.angiedev.sheystore.data.model.remote

import com.google.gson.annotations.SerializedName

data class CollectionResponse<T> (
    @SerializedName("documents")
    val documents: List<DocumentResponse<T>> = emptyList()
)

data class DocumentResponse <T> (
    @SerializedName("name")
    val name: String = "",
    @SerializedName("fields")
    val fields: T? = null,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("updateTime")
    val updateTime: String = ""
)

data class IntegerResponse(
    @SerializedName("integerValue")
    val integerValue: String = ""
)

data class StringResponse(
    @SerializedName("stringValue")
    val stringValue: String = ""
)

data class DoubleResponse(
    @SerializedName("doubleValue")
    val doubleValue: Double = 0.0
)



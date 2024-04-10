package com.angiedev.sheystore.data.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseArray(json: String): T {
    val typeToken = object : TypeToken<T>() { }.type
    val gson = GsonBuilder().create()
    return gson.fromJson(json, typeToken)
}
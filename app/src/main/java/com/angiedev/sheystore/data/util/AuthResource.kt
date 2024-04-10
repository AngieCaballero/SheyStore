package com.angiedev.sheystore.data.util

sealed class AuthResource<out T> {
    data class Success<T>(val data: T): AuthResource<T>()
    data class Error(val errorMessage: String): AuthResource<Nothing>()
}

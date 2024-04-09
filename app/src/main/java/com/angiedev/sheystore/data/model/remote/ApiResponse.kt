package com.angiedev.sheystore.data.model.remote

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Throwable?) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}
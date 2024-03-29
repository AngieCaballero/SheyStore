package com.angiedev.sheystore.data.repository.auth

interface IAuthenticationRepository {

    suspend fun isAuthenticate(): Boolean
}
package com.angiedev.sheystore.data.datasource.remote

import com.angiedev.sheystore.domain.entities.user.UserEntity

class UsersMockDataSource {

    private val usersList = mutableListOf(
        UserEntity(
            fullName = "Jackson Montenegro",
            gender = "Hombre",
            phone = "+15551234567",
            photo = "",
            role = "Comprador",
            username = "Mjackson22"
        ),
        UserEntity(
            fullName = "Test 1",
            gender = "Hombre",
            phone = "+15551234567",
            photo = "https://firebasestorage.googleapis.com/v0/b/sheystore-d7393.appspot.com/o/user-photos%2F1718865603158.jpg?alt=media&token=31224355-f035-48cf-a1b5-cbcc0a373263",
            role = "Comprador",
            username = "Test 12"
        ),
        UserEntity(
            fullName = "Anya",
            gender = "Hombre",
            phone = "55667788",
            photo = "",
            role = "Administrador",
            username = "tawa98"
        )
    )

    fun getUsers() = usersList

    fun getUser(username: String): UserEntity? = usersList.find { it.username == username }

    fun addUser(user: UserEntity): Boolean {
        usersList.add(user)
        return true
    }

    fun updateUser(username: String, updatedUser: UserEntity): Boolean {
        val index = usersList.indexOfFirst { it.username == username }
        return if (index != -1) {
            usersList[index] = updatedUser
            true
        } else {
            false
        }
    }

    fun deleteUser(username: String): Boolean {
        val index = usersList.indexOfFirst { it.username == username }
        return if (index != -1) {
            usersList.removeAt(index)
            true
        } else {
            false
        }
    }
}
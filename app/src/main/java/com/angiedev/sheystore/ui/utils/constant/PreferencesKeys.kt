package com.angiedev.sheystore.ui.utils.constant

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val USER_ID = intPreferencesKey("user_id")
    val TOKEN = stringPreferencesKey("token")
    val PASSWORD =  stringPreferencesKey("password")
    val EMAIL = stringPreferencesKey("email")
    val ROLE = stringPreferencesKey("role")
    val USERNAME = stringPreferencesKey("username")
    val GENDER = stringPreferencesKey("gender")
    val FULL_NAME = stringPreferencesKey("fullName")
    val PHOTO = stringPreferencesKey("photo")
    val PHONE = stringPreferencesKey("phone")
}
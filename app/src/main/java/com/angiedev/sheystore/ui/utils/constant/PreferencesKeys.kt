package com.angiedev.sheystore.ui.utils.constant

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
    val TIME_SESSION =  longPreferencesKey("time_session")
    val EMAIL = stringPreferencesKey("email")
    val ROLE = stringPreferencesKey("role")
    val USERNAME = stringPreferencesKey("username")
    val FULL_NAME = stringPreferencesKey("fullName")
    val PHOTO = stringPreferencesKey("photo")
    val PHONE = stringPreferencesKey("phone")
}
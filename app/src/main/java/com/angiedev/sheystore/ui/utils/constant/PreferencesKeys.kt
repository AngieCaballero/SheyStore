package com.angiedev.sheystore.ui.utils.constant

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
    val TIME_SESSION =  longPreferencesKey("time_session")
}
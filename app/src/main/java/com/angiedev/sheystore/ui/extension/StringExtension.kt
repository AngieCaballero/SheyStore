package com.angiedev.sheystore.ui.extension

import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseColor() = try {
    if (this.contains("#")) {
        Color.parseColor(this)
    } else {
        Color.parseColor("#${this}")
    }
} catch (e: Exception) {
    Log.e("Error to trying parse color", e.message.toString())
    Color.parseColor("#717171")
}

fun String.formattedDate(): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.parse(this)
}

fun String.validateEmail() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.validatePassword() = length >= 6
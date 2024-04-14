package com.angiedev.sheystore.ui.utils.extension

fun Long.getHours(timeSession: Long) = ((this - timeSession) / 1000) / 3600

fun Long?.orZero(): Long = this ?: 0L
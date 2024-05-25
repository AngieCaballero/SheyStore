package com.angiedev.sheystore.ui.utils.helper

import java.util.Calendar

fun getGreetingMessage() : String {
    val calendar = Calendar.getInstance()
    val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

    return when(timeOfDay) {
        in 5..11 -> "¡Buenos días!"
        in 12..17 -> "¡Buenas tardes!"
        in 18..23 -> "¡Buenas noches!"
        else -> "¡Hola!"
    }
}
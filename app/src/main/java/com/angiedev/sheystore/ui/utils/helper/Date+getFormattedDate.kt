package com.angiedev.sheystore.ui.utils.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getFormattedDate(
    inputFormat: String
): String = SimpleDateFormat(inputFormat, Locale("es", "ES")).format(this)
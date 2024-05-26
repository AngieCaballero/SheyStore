package com.angiedev.sheystore.ui.utils.extension

import android.content.Context
import android.util.DisplayMetrics

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)

fun Float.pxToDp(context: Context): Float =
    (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
package com.angiedev.sheystore.ui.utils.extension

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationViewExtension @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {


    companion object {
        private const val MAX_ITEM_COUNT = 10
    }

    override fun getMaxItemCount(): Int {
        return MAX_ITEM_COUNT
    }
}
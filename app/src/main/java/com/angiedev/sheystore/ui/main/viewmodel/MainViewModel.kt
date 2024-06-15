package com.angiedev.sheystore.ui.main.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val CART_ID = "cartId"
    }

    var cartId = 0

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CART_ID, cartId)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cartId = savedInstanceState.getInt(CART_ID)
    }
}
package com.angiedev.sheystore.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.domain.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    val cart: MutableList<CartItem> = mutableListOf()

}
package com.angiedev.sheystore.ui.shopping.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentShoppingBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : BaseFragment<FragmentShoppingBinding>() {

    override fun getViewBinding() = FragmentShoppingBinding.inflate(layoutInflater)

}
package com.angiedev.sheystore.ui.cart.view

import com.angiedev.sheystore.databinding.FragmentCartBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    override fun getViewBinding() = FragmentCartBinding.inflate(layoutInflater)

}
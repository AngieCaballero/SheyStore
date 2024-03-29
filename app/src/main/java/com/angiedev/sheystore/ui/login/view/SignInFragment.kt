package com.angiedev.sheystore.ui.login.view

import android.view.View
import com.angiedev.sheystore.databinding.FragmentSignInBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentSignInBinding.inflate(layoutInflater)

}
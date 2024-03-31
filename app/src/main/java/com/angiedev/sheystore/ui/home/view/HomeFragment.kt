package com.angiedev.sheystore.ui.home.view

import androidx.fragment.app.viewModels
import com.angiedev.sheystore.databinding.FragmentHomeBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun setObservers() {
        super.setObservers()
    }

}
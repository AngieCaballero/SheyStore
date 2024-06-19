package com.angiedev.sheystore.ui.modules.login.landingLogin.view

import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentLandingLoginBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingLoginFragment : BaseFragment<FragmentLandingLoginBinding>() {

    override var isBottomNavVisible = View.GONE
    override fun getViewBinding() = FragmentLandingLoginBinding.inflate(layoutInflater)

    override fun setListeners() {
        super.setListeners()
        binding.landingLoginSignIn.setOnClickListener {
            findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToSignInFragment())
        }

        binding.landingLoginCreateAccount.setOnClickListener {
            findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToCreateAccountFragment())
        }
    }
}
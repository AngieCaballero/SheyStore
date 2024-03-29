package com.angiedev.sheystore.ui.landingLogin.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentLandingLoginBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class LandingLoginFragment : BaseFragment<FragmentLandingLoginBinding>() {

    override var isBottomNavVisible = View.GONE
    override fun getViewBinding() = FragmentLandingLoginBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
    }

    override fun setListeners() {
        super.setListeners()
        binding.landingLoginSignIn.setOnClickListener {
            findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToSignInFragment())
        }

        binding.landingLoginCreateAccount.setOnClickListener {
            findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToCreateAccountFragment())
        }

        binding.landingLoginGoogleButton.setOnClickListener {
            // Request login with Google
            Toast.makeText(requireContext(), "Feature not yet implemented", Toast.LENGTH_SHORT).show()
        }
    }

}
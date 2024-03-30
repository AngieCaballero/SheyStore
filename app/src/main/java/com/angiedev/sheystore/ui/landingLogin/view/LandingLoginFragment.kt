package com.angiedev.sheystore.ui.landingLogin.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.AuthResource
import com.angiedev.sheystore.databinding.FragmentLandingLoginBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingLoginFragment : BaseFragment<FragmentLandingLoginBinding>() {

    override var isBottomNavVisible = View.GONE
    private val viewModel: LoginViewModel by viewModels()
    override fun getViewBinding() = FragmentLandingLoginBinding.inflate(layoutInflater)

    private val googleSignInLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
            }
        }

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
            viewModel.signInWithGoogle(googleSignInLauncher)
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.isAuthored.observe(viewLifecycleOwner) { isAuthored ->
            if (isAuthored) findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToNavHome())
        }

        viewModel.handleSignInResult.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AuthResource.Error -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Success -> {
                    val credential = GoogleAuthProvider.getCredential(response.data.idToken, null)
                    viewModel.signInWithGoogleCredential(credential)
                }

                else -> { }
            }
        }

        viewModel.sigInWithGoogleCredential.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AuthResource.Error -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Success -> {
                    if (response == null) {
                        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        return@observe
                    }
                    findNavController().navigate(LandingLoginFragmentDirections.actionLandingLoginFragmentToNavHome())
                }

                else -> { }
            }
        }
    }

}
package com.angiedev.sheystore.ui.login.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentSignInBinding
import com.angiedev.sheystore.databinding.LoginComponentBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.utils.extension.validateEmail
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.main.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private var _loginComponentBinding: LoginComponentBinding? = null
    private val loginComponentBinding get() = _loginComponentBinding!!
    private val viewModel: LoginViewModel by viewModels()

    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentSignInBinding.inflate(layoutInflater).also {
        _loginComponentBinding = LoginComponentBinding.bind(it.root)
    }

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupUI()
    }

    override fun setListeners() {
        super.setListeners()
        binding.createAccountArrowBack.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        loginComponentBinding.apply {

            createAccountButton.setOnClickListener {
                viewModel.validateCredentials(
                    createAccountEmail.text.toString(),
                    createAccountPassword.text.toString()
                )
            }

            createAccountSignIn.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToCreateAccountFragment())
            }

            createAccountSignInGoogle.setOnClickListener {
                // Request login with Google
                Toast.makeText(requireContext(), "Feature not yet implemented", Toast.LENGTH_SHORT).show()
            }

            createAccountPassword.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && text.toString().validatePassword()) {
                    createAccountPassword.error = null
                }
            }

            createAccountEmail.doOnTextChanged { text, _, _, _ ->
                if (text.toString().validateEmail()) {
                    createAccountEmailContainer.setEndIconDrawable(R.drawable.ic_check)
                    createAccountEmailContainer.error = null
                } else {
                    createAccountEmailContainer.setEndIconDrawable(R.drawable.ic_check)
                }
            }
        }
    }

    private fun navigateToHomeModule() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToNavHome())
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.password.observe(viewLifecycleOwner) { message ->
            loginComponentBinding.createAccountPasswordContainer.error = message
        }

        viewModel.username.observe(viewLifecycleOwner) { message ->
            loginComponentBinding.createAccountEmailContainer.error = message
        }
    }

    private fun setupUI() {
        loginComponentBinding.apply {
            createAccountButton.text = resources.getText(R.string.iniciar_sesi_n)
            loginComponentHeaderText.text = resources.getText(R.string.sign_in_with_account)
            loginComponentLabelQuestion.text = resources.getText(R.string.not_have_account)
            createAccountSignIn.text = resources.getText(R.string.crear_una_cuenta)
        }
    }

}
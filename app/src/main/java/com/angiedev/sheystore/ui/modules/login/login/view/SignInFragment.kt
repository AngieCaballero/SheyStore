package com.angiedev.sheystore.ui.modules.login.login.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.databinding.FragmentSignInBinding
import com.angiedev.sheystore.databinding.LoginComponentBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.login.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.utils.extension.validateEmail
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private var _loginComponentBinding: LoginComponentBinding? = null
    override var isBottomNavVisible = View.GONE
    private val loginComponentBinding get() = _loginComponentBinding!!
    private val viewModel: LoginViewModel by viewModels()

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
                    createAccountPassword.text.toString(),
                    true
                )
            }

            createAccountSignIn.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToCreateAccountFragment())
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

    override fun setObservers() {
        super.setObservers()
        viewModel.password.observe(viewLifecycleOwner) { message ->
            loginComponentBinding.createAccountPasswordContainer.error = message
        }

        viewModel.username.observe(viewLifecycleOwner) { message ->
            loginComponentBinding.createAccountEmailContainer.error = message
        }

        viewModel.signInWithEmailAndPassword.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AuthResource.Error -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Success -> {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToNavHome())
                }
            }
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
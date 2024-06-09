package com.angiedev.sheystore.ui.login.view

import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.databinding.FragmentCreateAccountBinding
import com.angiedev.sheystore.databinding.LoginComponentBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.utils.extension.validateEmail
import com.angiedev.sheystore.ui.utils.extension.validatePassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>() {

    private var _loginComponentBinding: LoginComponentBinding? = null
    private val loginComponentBinding get() = _loginComponentBinding!!

    private val viewModel: LoginViewModel by viewModels()
    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentCreateAccountBinding.inflate(layoutInflater).also {
        _loginComponentBinding = LoginComponentBinding.bind(it.root)
    }

    override fun setListeners() {
        super.setListeners()
        loginComponentBinding.apply {
            createAccountButton.setOnClickListener {
                viewModel.validateCredentials(
                    createAccountEmail.text.toString(),
                    createAccountPassword.text.toString(),
                    false
                )
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
        binding.createAccountArrowBack.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        loginComponentBinding.createAccountSignIn.setOnClickListener {
            findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToSignInFragment())
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

        viewModel.createUserWithEmailAndPassword.observe(viewLifecycleOwner) { response ->
            when(response) {
                is AuthResource.Error -> {
                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Success -> {
                    findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToFillYourProfileFragment())
                }
            }
        }
    }
}
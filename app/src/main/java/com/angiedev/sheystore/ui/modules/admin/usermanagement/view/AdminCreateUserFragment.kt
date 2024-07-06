package com.angiedev.sheystore.ui.modules.admin.usermanagement.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.util.AuthResource
import com.angiedev.sheystore.databinding.FragmentAdminCreateUserBinding
import com.angiedev.sheystore.domain.entities.user.UserEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.admin.usermanagement.viewmodel.UserManagementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminCreateUserFragment: BaseFragment<FragmentAdminCreateUserBinding>() {

    override var isBottomNavVisible = View.VISIBLE

    private val viewModel: UserManagementViewModel by viewModels()

    override fun getViewBinding() = FragmentAdminCreateUserBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupAdapters()
    }

    private fun setupAdapters() {
        binding.createUserSexEditText.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listOf("Hombre", "Mujer")
            )
        )
        binding.createUserRoleEditText.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listOf("Comprador", "Vendedor")
            )
        )
    }

    override fun setListeners() {
        super.setListeners()
        binding.createUserButton.setOnClickListener {
            viewModel.createUser(
                binding.createUserEmailEditText.text.toString(),
                binding.createUserPasswordEditText.text.toString()
            )
        }
        binding.createUserToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.userCreated.observe(viewLifecycleOwner) {
            when(it) {
                is AuthResource.Success -> viewModel.updateUser(
                    it.data.id,
                    UserEntity(
                        id = it.data.id,
                        fullName = binding.createUserFullNameEditText.text.toString(),
                        username = binding.createUserUsernameEditText.text.toString(),
                        phone = binding.createUserPhoneEditText.text.toString(),
                        gender = binding.createUserSexEditText.text.toString(),
                        role = binding.createUserRoleEditText.text.toString(),
                        photo = "String"
                    )
                )
                is AuthResource.Error -> { }
            }
        }
        viewModel.userUpdated.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> findNavController().popBackStack()
                is ApiResponse.Error -> {}
                is ApiResponse.Loading -> {  }
            }
        }
    }
}
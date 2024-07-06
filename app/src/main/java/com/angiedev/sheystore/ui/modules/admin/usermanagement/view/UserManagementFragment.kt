package com.angiedev.sheystore.ui.modules.admin.usermanagement.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentUsersManagementBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.admin.usermanagement.adapter.UserListAdapter
import com.angiedev.sheystore.ui.modules.admin.usermanagement.viewmodel.UserManagementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserManagementFragment : BaseFragment<FragmentUsersManagementBinding>() {
    private val viewModel: UserManagementViewModel by viewModels()
    private val usersAdapter = UserListAdapter(this::select)

    private var selectedId: Int? = null

    override fun getViewBinding() = FragmentUsersManagementBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getUsers()
    }

    override fun setListeners() {
        super.setListeners()

        binding.btnDeleteUser.setOnClickListener {
            delete()
        }

        binding.btnAddUser.setOnClickListener {
            findNavController().navigate(UserManagementFragmentDirections.actionUserManagementFragmentToAdminCreateUserFragment())
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.users.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    usersAdapter.submitList(it.data)
                }
                else -> {}
            }
        }

    }

    private fun select(userId: Int) {
        usersAdapter.currentList.forEachIndexed { index, _ ->
            (binding.rvUsers.findViewHolderForAdapterPosition(index) as? UserListAdapter.UserViewHolder)?.toggleSelection(
                false
            )
            selectedId = userId
        }

        usersAdapter.currentList.indexOfFirst { it.id == userId }.let {
            if (it != 0) {
                (binding.rvUsers.findViewHolderForAdapterPosition(it) as? UserListAdapter.UserViewHolder)?.toggleSelection(
                    true
                )
            }
        }
    }

    private fun delete() {
        selectedId?.let { userId ->
            viewModel.deleteUser(userId)
        }
    }

    private fun setupRecyclerView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this.context)
        binding.rvUsers.adapter = usersAdapter
    }
}

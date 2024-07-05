package com.angiedev.sheystore.ui.modules.admin.usermanagement

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.users.UsersRepositoryImpl
import com.angiedev.sheystore.databinding.FragmentUsersManagementBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserManagementFragment : BaseFragment<FragmentUsersManagementBinding>() {

    override fun getViewBinding() = FragmentUsersManagementBinding.inflate(layoutInflater)
    private val usersAdapter = UserListAdapter(this::select)

    @Inject
    lateinit var repositoryImpl: UsersRepositoryImpl

    private var selectedUsername: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUsers()
        setClickEvents()
    }

    private fun setClickEvents() {

        binding.btnDeleteUser.setOnClickListener {
            delete()
        }
    }

    private fun select(username: String) {

        usersAdapter.currentList.forEachIndexed { index, _ ->

            (binding.rvUsers.findViewHolderForAdapterPosition(index) as? UserListAdapter.UserViewHolder)?.toggleSelection(
                false
            )
            selectedUsername = username
        }

        usersAdapter.currentList.indexOfFirst { it.username == username }.let {

            if (it != -1) {
                (binding.rvUsers.findViewHolderForAdapterPosition(it) as? UserListAdapter.UserViewHolder)?.toggleSelection(
                    true
                )
            }
        }
    }

    private fun fetchUsers() {
        lifecycleScope.launch {

            repositoryImpl.getAllUsers().let {

                if (it is ApiResponse.Success) {

                    setupRecyclerView()
                    usersAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun delete() {
        selectedUsername?.let { username ->
            lifecycleScope.launch {

                repositoryImpl.deleteUser(username)
                fetchUsers()
                selectedUsername = null // Limpiar la selección después de eliminar
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this.context)
        binding.rvUsers.adapter = usersAdapter
    }
}

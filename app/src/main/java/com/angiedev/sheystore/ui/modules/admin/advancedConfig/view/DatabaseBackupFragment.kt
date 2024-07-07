package com.angiedev.sheystore.ui.modules.admin.advancedConfig.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentDatabaseBackupBinding
import com.angiedev.sheystore.domain.entities.user.RoleType
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.admin.advancedConfig.viewmodel.DatabaseBackupViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DatabaseBackupFragment : BaseFragment<FragmentDatabaseBackupBinding>() {

    override var isBottomNavVisible = View.GONE
    private val viewModel: DatabaseBackupViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()

    override fun getViewBinding() = FragmentDatabaseBackupBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setUI()
    }

    private fun setUI() {
        if (userDataViewModel.readValue(PreferencesKeys.ROLE) == RoleType.Admin.value) {
            binding.roleAdmin.setVisible()
            binding.roleNotAdmin.setGone()
        } else {
            binding.roleAdmin.setGone()
            binding.roleNotAdmin.setVisible()
        }
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentBackupButton.setOnClickListener {
                viewModel.downloadDatabaseBackup()
            }

            fragmentBackupToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.downloadFile.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}
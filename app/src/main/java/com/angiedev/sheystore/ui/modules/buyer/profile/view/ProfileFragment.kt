package com.angiedev.sheystore.ui.modules.buyer.profile.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentProfileBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.main.view.MainActivity
import com.angiedev.sheystore.ui.modules.buyer.profile.adapter.ProfileItemsAdapter
import com.angiedev.sheystore.ui.modules.buyer.profile.adapter.ProfileItemsListener
import com.angiedev.sheystore.ui.modules.buyer.profile.data.ProfileItem
import com.angiedev.sheystore.ui.modules.buyer.profile.data.ProfileItemsType
import com.angiedev.sheystore.ui.modules.login.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ProfileItemsListener {

    private val userDataViewModel: UserDataViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setUI()
    }

    override fun setObservers() {
        super.setObservers()
        loginViewModel.signOut.observe(viewLifecycleOwner) {
            if (it) {
                (activity as MainActivity).selectBottomNav(R.id.item_login)
                loginViewModel.resetSignOutState()
            }
        }
    }

    private fun setUI() {
        with(binding) {
            val photo = userDataViewModel.readValue(PreferencesKeys.PHOTO)
            val username = userDataViewModel.readValue(PreferencesKeys.FULL_NAME)
            val phone = userDataViewModel.readValue(PreferencesKeys.PHONE)
            binding.profileUserName.text = username
            binding.profilePhone.text = phone
            binding.profileItems.adapter = ProfileItemsAdapter(this@ProfileFragment)
            Glide.with(requireContext())
                .load(photo)
                .placeholder(R.drawable.ic_user_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImage)
        }

    }

    override fun onProfileItemClickListener(item: ProfileItem) {
        when(item.type) {
            ProfileItemsType.Address -> { /* TODO */ }
            ProfileItemsType.EditProfile -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToFillYourProfileFragment2())
            }
            ProfileItemsType.HelpCenter -> { /* TODO */ }
            ProfileItemsType.InviteFriends -> { /* TODO */ }
            ProfileItemsType.Order -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOrdersTabsFragment2())
            }
            ProfileItemsType.Logout -> {
                loginViewModel.signOut()
            }
            ProfileItemsType.Notification -> { /* TODO */ }
            ProfileItemsType.Payment -> { /* TODO */ }
            ProfileItemsType.PrivacyPolicy -> { /* TODO */ }
            ProfileItemsType.Security -> { /* TODO */ }
            ProfileItemsType.UsersConfiguration -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToDatabaseBackupFragment())
            }
        }
    }

}
package com.angiedev.sheystore.ui.home.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentHomeBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun setObservers() {
        super.setObservers()
    }

    override fun setListeners() {
        super.setListeners()
        binding.fragmentHomeHeaderProfile.apply {
            headerProfileInfoNotifications.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotificationsFragment())
            }

            headerProfileInfoHeart.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWishListFragment())
            }
        }

        binding.specialsOffersViewAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSpecialsOffersFragment())
        }
    }

}
package com.angiedev.sheystore.ui.modules.buyer.notifications.view

import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentNotificationsBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    override var isBottomNavVisible = View.GONE
    override fun getViewBinding() = FragmentNotificationsBinding.inflate(layoutInflater)

    override fun setListeners() {
        super.setListeners()
        binding.notificationsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
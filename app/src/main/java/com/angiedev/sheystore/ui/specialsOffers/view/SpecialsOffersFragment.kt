package com.angiedev.sheystore.ui.specialsOffers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentSpecialsOffersBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class SpecialsOffersFragment : BaseFragment<FragmentSpecialsOffersBinding>() {

    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentSpecialsOffersBinding.inflate(layoutInflater)

    override fun setListeners() {
        super.setListeners()
        binding.wishlistToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
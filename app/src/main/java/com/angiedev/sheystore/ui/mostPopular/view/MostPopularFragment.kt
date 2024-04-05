package com.angiedev.sheystore.ui.mostPopular.view

import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentMostPopularBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class MostPopularFragment : BaseFragment<FragmentMostPopularBinding>() {

    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentMostPopularBinding.inflate(layoutInflater)

    override fun setListeners() {
        super.setListeners()
        binding.mostPopularToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
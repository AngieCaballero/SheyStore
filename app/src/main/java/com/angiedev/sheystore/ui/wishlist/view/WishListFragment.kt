package com.angiedev.sheystore.ui.wishlist.view

import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentWishListBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class WishListFragment : BaseFragment<FragmentWishListBinding>() {

    override var isBottomNavVisible = View.GONE
    override fun getViewBinding() = FragmentWishListBinding.inflate(layoutInflater)

    override fun setListeners() {
        super.setListeners()
        binding.wishlistToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
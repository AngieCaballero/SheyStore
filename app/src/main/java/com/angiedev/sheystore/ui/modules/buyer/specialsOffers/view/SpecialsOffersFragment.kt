package com.angiedev.sheystore.ui.modules.buyer.specialsOffers.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentSpecialsOffersBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.buyer.home.viewmodel.HomeViewModel
import com.angiedev.sheystore.ui.modules.buyer.specialsOffers.adapter.SpecialsOffersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialsOffersFragment : BaseFragment<FragmentSpecialsOffersBinding>() {

    override var isBottomNavVisible = View.GONE
    private var specialsOffersAdapter: SpecialsOffersAdapter? = null
    private val homeViewModel: HomeViewModel by viewModels()

    override fun getViewBinding() = FragmentSpecialsOffersBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupSpecialsOffersAdapter()
        homeViewModel.getSpecialsOffers()
    }

    private fun setupSpecialsOffersAdapter() {
        specialsOffersAdapter = SpecialsOffersAdapter()
        binding.specialsOffersRv.adapter = specialsOffersAdapter
    }

    override fun setListeners() {
        super.setListeners()
        binding.specialsOffersSwipeToRefresh.setOnRefreshListener {
            homeViewModel.getSpecialsOffers()
        }
        binding.wishlistToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setObservers() {
        super.setObservers()
        homeViewModel.specialsOffers.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    binding.specialsOffersSwipeToRefresh.isRefreshing = false
                    specialsOffersAdapter?.submitList(response.data)
                }
            }
        }
    }

}
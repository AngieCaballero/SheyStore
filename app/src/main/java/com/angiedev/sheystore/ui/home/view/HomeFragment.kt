package com.angiedev.sheystore.ui.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.data.entities.SpecialsOffersEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.databinding.FragmentHomeBinding
import com.angiedev.sheystore.databinding.ItemCategoryChipsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.home.view.adapter.CategoryAdapter
import com.angiedev.sheystore.ui.home.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var categoryAdapter: CategoryAdapter? = null

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        homeViewModel.getSpecialsOffers()
        homeViewModel.getCategories()
        setupCategoryAdapter()
    }

    private fun setupCategoryAdapter() {
        categoryAdapter = CategoryAdapter()
        binding.fragmentHomeCategoryRv.adapter = categoryAdapter
    }

    override fun setObservers() {
        super.setObservers()
        homeViewModel.specialsOffers.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.exception?.toString(), Toast.LENGTH_SHORT).show()
                }
                ApiResponse.Loading -> {
                    // Show Loading Screen
                }
                is ApiResponse.Success -> {
                    loadSpecialsOffers(response.data.first())
                }
            }
        }

        homeViewModel.categories.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    // Load categories recycler view
                    categoryAdapter?.submitList(response.data)
                    setupMostPopularCategoryChips(response.data)
                }
            }
        }
    }

    private fun setupMostPopularCategoryChips(data: List<CategoryEntity>) {
        data.forEach {
            val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
            chip.root.apply {
                text = it.name
                id = Random().nextInt()
            }
            binding.fragmentHomeMostPopular.mostPopularChipsGroup.addView(chip.root)
        }
    }

    private fun loadSpecialsOffers(specialsOffers: SpecialsOffersEntity) {
        with(binding.fragmentHomeSpecialsOffers) {
            specialsOffersDiscount.text = specialsOffers.percentDiscount
            specialsOffersTitle.text = specialsOffers.title
            specialsOffersShortDescription.text = specialsOffers.description
            Glide.with(requireContext())
                .load(specialsOffers.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(specialsOffersImage)
        }
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

        binding.fragmentHomeMostPopular.apply {
            categoryMostPopularViewAll.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMostPopularFragment())
            }

            mostPopularChipsGroup.setOnCheckedStateChangeListener { chipGroup, ints ->
                // Event Chip Checked
            }
        }

        binding.fragmentHomeSearchBar.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

}
package com.angiedev.sheystore.ui.home.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.domain.ScreenProducts
import com.angiedev.sheystore.data.model.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.domain.entities.specialsOffers.SpecialOfferEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentHomeBinding
import com.angiedev.sheystore.databinding.ItemCategoryChipsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.home.SortFilterBottomSheetDialog
import com.angiedev.sheystore.ui.home.view.adapter.CategoryAdapter
import com.angiedev.sheystore.ui.home.viewmodel.HomeViewModel
import com.angiedev.sheystore.ui.mostPopular.view.adapter.ProductAdapter
import com.angiedev.sheystore.ui.mostPopular.viewmodel.ProductViewModel
import com.angiedev.sheystore.ui.product.adapter.ProductItemListener
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.QuerySelector
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setVisible
import com.angiedev.sheystore.ui.utils.helper.getGreetingMessage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Random

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ProductItemListener {

    companion object {
        private const val CHECKED_CATEGORY_SELECTED = "checkedCategorySelected"
    }

    private val homeViewModel: HomeViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private var categoryAdapter: CategoryAdapter? = null
    private var productAdapter: ProductAdapter? = null
    private var productSearchAdapter: ProductAdapter? = null
    private val productList: MutableList<ProductEntity> = mutableListOf()
    private val categoryList: MutableList<CategoryEntity> = mutableListOf()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        homeViewModel.getSpecialsOffers()
        productViewModel.getProducts()
        homeViewModel.getCategories()
        setupAdapters()
        setupUI()
    }

    private fun setupUI() {
        lifecycleScope.launch {
            with(binding.fragmentHomeHeaderProfile) {
                headerProfileInfoGreeting.text = getGreetingMessage()
                headerProfileInfoName.text = userDataViewModel.readValue(PreferencesKeys.USERNAME)
                val photo = userDataViewModel.readValue(PreferencesKeys.PHOTO)
                Glide.with(requireContext())
                    .load(photo)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(headerProfileInfoImage)
            }
        }
        binding.fragmentHomeSearchView.inflateMenu(R.menu.menu_search_bar)
    }

    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter()
        binding.fragmentHomeCategoryRv.adapter = categoryAdapter
        productAdapter = ProductAdapter(this)
        binding.homeFragmentMostPopularProductsRv.adapter = productAdapter
        productSearchAdapter = ProductAdapter(this)
        binding.fragmentHomeLayoutResults.fragmentSearchResults.adapter = productSearchAdapter
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
                    categoryList.clear()
                    categoryList.addAll(response.data)
                    categoryAdapter?.submitList(response.data)
                    setupMostPopularCategoryChips()
                }
            }
        }

        productViewModel.products.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    productList.clear()
                    productList.addAll(response.data)
                    productViewModel.setProductsList(response.data)
                }
            }
        }

        homeViewModel.filteredList.observe(viewLifecycleOwner) {
            productAdapter?.filterBy(it)
        }

        homeViewModel.filteredByNameList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.fragmentHomeLayoutResults.fragmentSearchResults.setGone()
                binding.fragmentHomeLayoutResults.fragmentSearchViewNotFound.setVisible()
            } else {
                binding.fragmentHomeLayoutResults.fragmentSearchResults.setVisible()
                binding.fragmentHomeLayoutResults.fragmentSearchViewNotFound.setGone()
                productSearchAdapter?.filterBy(it)
            }
        }
    }

    private fun setupMostPopularCategoryChips() {
        with(binding.fragmentHomeMostPopular.mostPopularChipsGroup) {
            if (childCount > 0) return@with
            addChips("Todo")
            categoryList.forEach {item ->
                addChips(item.name)
            }
            check(getChildAt(0).id)
        }
    }

    private fun addChips(title: String) {
        val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
        chip.root.apply {
            text = title
            id = Random().nextInt()
        }
        binding.fragmentHomeMostPopular.mostPopularChipsGroup.addView(chip.root)
    }

    private fun loadSpecialsOffers(specialsOffers: SpecialOfferEntity) {
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
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMostPopularFragment(ScreenProducts.MY_WISH_LIST.typeScreen))
            }
        }

        binding.specialsOffersViewAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSpecialsOffersFragment())
        }

        binding.fragmentHomeMostPopular.apply {
            categoryMostPopularViewAll.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMostPopularFragment(ScreenProducts.MOST_POPULAR.typeScreen))
            }

            mostPopularChipsGroup.setOnCheckedStateChangeListener { chipGroup, ints ->
                // Event Chip Checked
                val selectedChip = chipGroup.findViewById<Chip>(ints.first())
                homeViewModel.filterByCategory(selectedChip.text.toString(), productList)
            }
        }

        binding.fragmentHomeSearchBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.menu_item_filter -> {
                    showSortFilterBottomSheetDialog()
                }
            }
            return@setOnMenuItemClickListener true
        }

        binding.fragmentHomeSearchView.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.menu_item_filter -> {
                    showSortFilterBottomSheetDialog()
                }
            }
            return@setOnMenuItemClickListener true
        }

        binding.fragmentHomeSearchView.addTransitionListener { _, _, transitionState2 ->
            when (transitionState2) {
                SearchView.TransitionState.SHOWING -> {
                    bottomNavigationVisibility(View.GONE)
                    binding.fragmentHomeHeaderProfile.root.setGone()
                    binding.fragmentHomeContentInfo.setGone()
                }
                SearchView.TransitionState.HIDDEN -> {
                    bottomNavigationVisibility(View.VISIBLE)
                    productSearchAdapter?.filterBy(emptyList())
                    binding.fragmentHomeContentInfo.setVisible()
                    binding.fragmentHomeHeaderProfile.root.setVisible()
                }
                else -> { }
            }
        }

        binding.fragmentHomeSearchView.editText.doAfterTextChanged {
            if (it.toString().isBlank()) {
                productSearchAdapter?.filterBy(emptyList())
                return@doAfterTextChanged
            }
            homeViewModel.filterByName(it.toString().lowercase(), productList)
        }

        binding.fragmentHomeSearchView.editText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener false
        }
    }

    private fun showSortFilterBottomSheetDialog() {
        SortFilterBottomSheetDialog.newInstance(
            bundleOf(
                SortFilterBottomSheetDialog.CATEGORIES to categoryList,
                SortFilterBottomSheetDialog.MAX_PRICE to productList.maxOf { it.price },
                SortFilterBottomSheetDialog.MIN_PRICE to productList.minOf { it.price }
            )
        ).apply {
            setOnApplyFilterListener { selectedCategory, selectedRating, selectedMinPrice, selectedMaxPrice ->
                val filterList = QuerySelector.Builder()
                    .setListToFilter(productList)
                    .category(selectedCategory)
                    .rating(selectedRating)
                    .minPrice(selectedMinPrice)
                    .maxPrice(selectedMaxPrice)
                    .build()
                productAdapter?.filterBy(filterList)
            }
        }.show(childFragmentManager, SortFilterBottomSheetDialog.TAG)
    }

    override fun onClickItem(productEntity: ProductEntity) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(productEntity.id))
    }
}
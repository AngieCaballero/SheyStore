package com.angiedev.sheystore.ui.mostPopular.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.data.entities.ProductEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.databinding.FragmentMostPopularBinding
import com.angiedev.sheystore.databinding.ItemCategoryChipsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.home.viewmodel.HomeViewModel
import com.angiedev.sheystore.ui.mostPopular.view.adapter.ProductAdapter
import com.angiedev.sheystore.ui.mostPopular.viewmodel.ProductViewModel
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setVisible
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class MostPopularFragment : BaseFragment<FragmentMostPopularBinding>() {

    override var isBottomNavVisible = View.GONE
    private val mostPopularArgs: MostPopularFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private var productAdapter: ProductAdapter? = null
    private val productList: MutableList<ProductEntity> = mutableListOf()

    override fun getViewBinding() = FragmentMostPopularBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        binding.mostPopularToolbar.title = mostPopularArgs.screenType
        setupAdapters()
        homeViewModel.getCategories()
        viewModel.getProducts()
    }

    private fun setupAdapters() {
        productAdapter = ProductAdapter()
        binding.mostPopularProductsRv.adapter = productAdapter
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                }
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    productList.addAll(response.data)
                    viewModel.setProductsList(response.data)
                }
            }
        }

        homeViewModel.categories.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    if (response.data.isEmpty()) {
                        binding.mostPopularLayoutNoResults.root.setVisible()
                        binding.mostPopularProductsRv.setGone()
                    } else {
                        binding.mostPopularLayoutNoResults.root.setGone()
                        binding.mostPopularProductsRv.setVisible()
                        setupMostPopularCategoryChips(response.data)
                    }
                }
            }
        }

        viewModel.filteredList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.mostPopularLayoutNoResults.root.setVisible()
                binding.mostPopularProductsRv.setGone()
            } else {
                binding.mostPopularLayoutNoResults.root.setGone()
                binding.mostPopularProductsRv.setVisible()
            }
            productAdapter?.filterBy(it)
        }
    }

    private fun setupMostPopularCategoryChips(data: List<CategoryEntity>) {
        data.forEach {
            val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
            chip.root.apply {
                text = it.name
                id = Random().nextInt()
            }
            binding.mostPopularChipsGroup.addView(chip.root)
        }
        binding.mostPopularChipsGroup.check(binding.mostPopularChipsGroup.getChildAt(0).id)
    }

    override fun setListeners() {
        super.setListeners()
        binding.mostPopularChipsGroup.setOnCheckedStateChangeListener { chipGroup, ints ->
            val selectedChip = chipGroup.findViewById<Chip>(ints.first())
            viewModel.filterBy(selectedChip.text.toString(), productList)
        }
        binding.mostPopularToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
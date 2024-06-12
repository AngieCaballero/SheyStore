package com.angiedev.sheystore.ui.shopping.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentShoppingBinding
import com.angiedev.sheystore.databinding.ItemCategoryChipsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.home.viewmodel.HomeViewModel
import com.angiedev.sheystore.ui.mostPopular.view.adapter.ProductAdapter
import com.angiedev.sheystore.ui.mostPopular.viewmodel.ProductViewModel
import com.angiedev.sheystore.ui.product.adapter.ProductItemListener
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setVisible
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class ShoppingFragment : BaseFragment<FragmentShoppingBinding>(), ProductItemListener {

    private val viewModel: ProductViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private var productAdapter: ProductAdapter? = null
    private val productList: MutableList<ProductEntity> = mutableListOf()

    override fun getViewBinding() = FragmentShoppingBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupAdapters()
        homeViewModel.getCategories()
        viewModel.getProducts()
    }

    private fun setupAdapters() {
        productAdapter = ProductAdapter(this)
        binding.shoppingProductsRv.adapter = productAdapter
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
                    productList.clear()
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
                        binding.shoppingLayoutNoResults.root.setVisible()
                        binding.shoppingProductsRv.setGone()
                    } else {
                        binding.shoppingLayoutNoResults.root.setGone()
                        binding.shoppingProductsRv.setVisible()
                        setupMostPopularCategoryChips(response.data)
                    }
                }
            }
        }

        viewModel.filteredProducts.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.shoppingLayoutNoResults.root.setVisible()
                binding.shoppingProductsRv.setGone()
            } else {
                binding.shoppingLayoutNoResults.root.setGone()
                binding.shoppingProductsRv.setVisible()
                productAdapter?.filterBy(it)
            }
        }
    }

    private fun setupMostPopularCategoryChips(data: List<CategoryEntity>) {
        if (binding.shoppingChipsGroup.childCount > 0) return
        addChips("Todo")
        data.forEach {
            addChips(it.name)
        }
        binding.shoppingChipsGroup.check(binding.shoppingChipsGroup.getChildAt(0).id)
    }

    private fun addChips(title: String) {
        val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
        chip.root.apply {
            text = title
            id = Random().nextInt()
        }
        binding.shoppingChipsGroup.addView(chip.root)
    }

    override fun setListeners() {
        super.setListeners()
        binding.shoppingChipsGroup.setOnCheckedStateChangeListener { chipGroup, ints ->
            val selectedChip = chipGroup.findViewById<Chip>(ints.first())
            viewModel.filterBy(selectedChip.text.toString(), productList)
        }
    }

    override fun onClickItem(productEntity: ProductEntity) {
        findNavController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToProductDetailsFragment(productEntity))
    }

}
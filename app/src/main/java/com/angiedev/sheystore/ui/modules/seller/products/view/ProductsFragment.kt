package com.angiedev.sheystore.ui.modules.seller.products.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentProductsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.seller.products.adapter.SellerProductAdapter
import com.angiedev.sheystore.ui.modules.seller.products.viewmodel.ProductsViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment: BaseFragment<FragmentProductsBinding>(), SellerProductAdapter.SellerProductListener {
    private val viewModel: ProductsViewModel by viewModels()
    private val userViewModel: UserDataViewModel by viewModels()
    private val adapter = SellerProductAdapter(this)
    private var userId = 0

    override fun getViewBinding() = FragmentProductsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        userId = userViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        setupAdapter()
        viewModel.getProducts(userId)
    }

    override fun setListeners() {
        super.setListeners()

        binding.sellerProductAddButton.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.toCreateProductFragment())
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()

                is ApiResponse.Loading -> { }

                is ApiResponse.Success -> adapter.submitList(response.data)
            }
        }

        viewModel.productUpdated.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(
                    requireContext(),
                    it.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                ApiResponse.Loading -> {}

                is ApiResponse.Success -> {
                    viewModel.getProducts(userId)
                }
            }
        }

        viewModel.productDeleted.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(
                    requireContext(),
                    it.toString(),
                    Toast.LENGTH_SHORT
                    ).show()

                is ApiResponse.Loading -> {}

                is ApiResponse.Success -> {
                    viewModel.getProducts(userId)
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.sellerProductRecycler.adapter = this.adapter
    }

    override fun onDeleteButtonClick(productId: Int) {
        viewModel.deleteProduct(productId)
    }

    override fun onEditButtonClick(productId: Int) {

    }

    override fun onDetailButtonClick(productId: Int) {
        /* TODO */
    }

}
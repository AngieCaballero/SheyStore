package com.angiedev.sheystore.ui.modules.seller.products.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentProductsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.seller.products.adapter.SellerProductAdapter

class ProductsFragment: BaseFragment<FragmentProductsBinding>(), SellerProductAdapter.SellerProductListener {

    private val adapter = SellerProductAdapter(this)

    override fun getViewBinding() = FragmentProductsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun setListeners() {
        super.setListeners()

        binding.sellerProductAddButton.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.toCreateProductFragment())
        }
    }

    private fun setupAdapter() {
        binding.sellerProductRecycler.adapter = this.adapter
    }

    override fun onDeleteButtonClick(productId: Int) {
        /* TODO */
    }

    override fun onEditButtonClick(productId: Int) {
        /* TODO */
    }

    override fun onDetailButtonClick(productId: Int) {
        /* TODO */
    }

}
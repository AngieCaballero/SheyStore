package com.angiedev.sheystore.ui.product.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angiedev.sheystore.data.entities.ProductDetailsEntity
import com.angiedev.sheystore.data.model.remote.ApiResponse
import com.angiedev.sheystore.databinding.FragmentProductDetailsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.mostPopular.viewmodel.ProductViewModel
import com.angiedev.sheystore.ui.product.QuantityStepperListener
import com.angiedev.sheystore.ui.product.adapter.ProductDetailsImagesViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {

    private val productViewModel: ProductViewModel by viewModels()
    private val productDetailsFragmentArgs: ProductDetailsFragmentArgs by navArgs()

    override fun getViewBinding() = FragmentProductDetailsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        productViewModel.getProductDetails(productDetailsFragmentArgs.productId)
    }

    override fun setListeners() {
        super.setListeners()
        binding.productDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.productDetailsQuantityStepper.setQuantityStepperListener(object : QuantityStepperListener {
            override fun onValueChanged(value: Int) {

            }
        })
    }

    override fun setObservers() {
        super.setObservers()
        productViewModel.productDetails.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { }
                is ApiResponse.Success -> {
                    setupUI(it.data)
                }
            }
        }
    }

    private fun setupUI(data: ProductDetailsEntity) {
        with(binding) {
            productDetailsCategory.text = data.category
            productDetailsDescription.text = data.description
            productDetailsName.text = data.name
            productDetailsRate.text = data.rating
            productDetailsPrice.text = data.price
            setupViewPagerImageProducts(data.images)
        }
    }

    private fun setupViewPagerImageProducts(images: List<String>) {
        binding.productDetailsImages.adapter = ProductDetailsImagesViewPagerAdapter(images)
        binding.productDetailsSpringDotsIndicator.attachTo(binding.productDetailsImages)
    }

}
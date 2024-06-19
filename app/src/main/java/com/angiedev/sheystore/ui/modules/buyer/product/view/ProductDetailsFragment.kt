package com.angiedev.sheystore.ui.modules.buyer.product.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angiedev.sheystore.R
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentProductDetailsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import com.angiedev.sheystore.ui.modules.buyer.mostPopular.viewmodel.ProductViewModel
import com.angiedev.sheystore.ui.modules.buyer.product.QuantityStepperListener
import com.angiedev.sheystore.ui.modules.buyer.product.adapter.ProductDetailsImagesViewPagerAdapter
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {

    private val productViewModel: ProductViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val productDetailsFragmentArgs: ProductDetailsFragmentArgs by navArgs()
    private var quantity = 1
    private var productDetailsEntity: ProductEntity? = null

    override fun getViewBinding() = FragmentProductDetailsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        productDetailsEntity = productDetailsFragmentArgs.product
        productDetailsEntity?.let {
            setupUI(it)
        }
    }

    override fun setListeners() {
        super.setListeners()
        binding.productDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.productDetailsQuantityStepper.setQuantityStepperListener(object :
            QuantityStepperListener {
            override fun onValueChanged(value: Int) {
                quantity = value
                with(binding.productDetailsAddToCar) {
                    alpha = if (value == 0) {
                        isEnabled = false
                        0.5f
                    } else {
                        isEnabled = true
                        1f
                    }
                }
                val price = productDetailsEntity?.price ?: 0.0
                binding.productDetailsPriceTotal.text = resources.getString(R.string.total_price, String.format(
                    Locale.getDefault(),"%.2f", (value.times(price))))
            }
        })

        binding.productDetailsAddToCar.setOnClickListener {
            val totalPrice = binding.productDetailsPriceTotal.text.removePrefix("$").toString().toDouble()
            productDetailsEntity?.let { product ->
                productViewModel.addProductToCart(
                    userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                    productId = product.id,
                    quantity = quantity,
                    totalPrice = totalPrice,
                    color = "#FF018786"
                )
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        productViewModel.cartItems.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUI(data: ProductEntity) {
        with(binding) {
            productDetailsCategory.text = data.category.name
            productDetailsDescription.text = data.description
            productDetailsName.text = data.name
            productDetailsRate.text = data.rate
            productDetailsPrice.text = data.price.toString()
            setupViewPagerImageProducts(data.presentationImages)
            binding.productDetailsPriceTotal.text = resources.getString(R.string.total_price, String.format(
                Locale.getDefault(),"%.2f", data.price
            ))
        }
    }

    private fun setupViewPagerImageProducts(images: List<String>) {
        binding.productDetailsImages.adapter = ProductDetailsImagesViewPagerAdapter(images)
        binding.productDetailsSpringDotsIndicator.attachTo(binding.productDetailsImages)
    }

}
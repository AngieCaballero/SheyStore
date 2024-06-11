package com.angiedev.sheystore.ui.cart.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.domain.entities.cart.CartEntity
import com.angiedev.sheystore.data.model.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentCartBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.cart.RemoveCartItemBottomSheetDialog
import com.angiedev.sheystore.ui.cart.RemoveCartItemBottomSheetDialog.Companion.CART_ITEM
import com.angiedev.sheystore.ui.cart.adapter.CartAdapter
import com.angiedev.sheystore.ui.cart.adapter.CartItemListener
import com.angiedev.sheystore.ui.cart.viewmodel.CartViewModel
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartItemListener {

    private val cartViewModel: CartViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private var cartAdapter: CartAdapter? = null
    private var itemToRemove = 0
    override fun getViewBinding() = FragmentCartBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupCartAdapter()
        cartViewModel.getCartItems(userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0)
    }

    override fun setListeners() {
        with(binding) {
            fragmentCartCheckout.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckoutFragment())
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        cartViewModel.cartItems.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    setUI(response.data.cartItems)
                }
            }
        }

        cartViewModel.deleteCartItem.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    cartAdapter?.removeItem(itemToRemove)
                }
            }
        }
    }
    private fun setUI(data: List<CartItemEntity>) {
        cartAdapter?.submitList(data)
        val totalPrice = data.sumOf { it.totalPrice }
        binding.fragmentCartPriceTotal.text = resources.getString(R.string.total_price, totalPrice.toString())
    }

    private fun setupCartAdapter() {
        cartAdapter = CartAdapter(this)
        binding.fragmentCartItemRv.adapter = cartAdapter
    }

    override fun onRemoveItem(cartItem: CartItemEntity, position: Int) {
        RemoveCartItemBottomSheetDialog.newInstance(
            bundleOf(CART_ITEM to cartItem)
        ).also {
            it.setOnRemoveCartItemListener {
                itemToRemove = position
                cartViewModel.removeProductFromCart(
                    userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                    cartItem.id
                )
            }
        }.show(
            childFragmentManager, "RemoveCartItemBottomSheetDialog"
        )
    }

    override fun onValueChangeQuantityStepper(value: Int, cartItem: CartItemEntity) {
        val totalPrice = value.times(cartItem.product.price)
        cartViewModel.addProductToCart(
            userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
            productId = cartItem.productId,
            quantity = value,
            totalPrice = totalPrice,
            color = "#FF018786"
        )
    }

}
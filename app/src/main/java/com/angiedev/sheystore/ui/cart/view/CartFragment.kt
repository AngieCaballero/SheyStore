package com.angiedev.sheystore.ui.cart.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.CartEntity
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

    private val mainViewModel: MainViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private var cartAdapter: CartAdapter? = null
    override fun getViewBinding() = FragmentCartBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupCartAdapter()
        cartViewModel.getCartItems(userDataViewModel.readValue(PreferencesKeys.EMAIL).orEmpty())
    }

    override fun setObservers() {
        super.setObservers()
        cartViewModel.cartItems.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    mainViewModel.cart.clear()
                    mainViewModel.cart.addAll(response.data)
                    setUI(response.data)
                }
            }
        }
    }
    private fun setUI(data: List<CartEntity>) {
        cartAdapter?.submitList(data)
        val totalPrice = data.sumOf { it.totalPrice.toDouble() }
        binding.fragmentCartPriceTotal.text = resources.getString(R.string.total_price, totalPrice.toString())
    }

    private fun setupCartAdapter() {
        cartAdapter = CartAdapter(this)
        binding.fragmentCartItemRv.adapter = cartAdapter
    }

    override fun onRemoveItem(cartItem: CartEntity, position: Int) {
        RemoveCartItemBottomSheetDialog.newInstance(
            bundleOf(CART_ITEM to cartItem)
        ).also {
            it.setOnRemoveCartItemListener {
                mainViewModel.cart.removeAt(position)
                cartViewModel.patchCartItems(
                    documentId = userDataViewModel.readValue(PreferencesKeys.EMAIL).orEmpty(),
                    cartItems = mainViewModel.cart
                )
            }
        }.show(
            childFragmentManager, "RemoveCartItemBottomSheetDialog"
        )
    }

    override fun onValueChangeQuantityStepper(value: Int) {
        TODO("Not yet implemented")
    }

}
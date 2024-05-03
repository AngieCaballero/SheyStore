package com.angiedev.sheystore.ui.cart.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.domain.CartItem
import com.angiedev.sheystore.databinding.FragmentCartBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.cart.RemoveCartItemBottomSheetDialog
import com.angiedev.sheystore.ui.cart.adapter.CartAdapter
import com.angiedev.sheystore.ui.cart.adapter.CartItemListener
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartItemListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var cartAdapter: CartAdapter? = null
    override fun getViewBinding() = FragmentCartBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupCartAdapter()
        setUI()
    }

    private fun setUI() {
        val totalPrice = mainViewModel.cart.sumOf { it.totalPrice.toDouble() }
        binding.fragmentCartPriceTotal.text = resources.getString(R.string.total_price, totalPrice.toString())
    }

    private fun setupCartAdapter() {
        cartAdapter = CartAdapter(this)
        binding.fragmentCartItemRv.adapter = cartAdapter
        cartAdapter?.submitList(mainViewModel.cart)
    }

    override fun onRemoveItem(cartItem: CartItem) {
        RemoveCartItemBottomSheetDialog.newInstance().show(
            childFragmentManager, "RemoveCartItemBottomSheetDialog"
        )
    }

    override fun onValueChangeQuantityStepper(value: Int) {
        TODO("Not yet implemented")
    }

}
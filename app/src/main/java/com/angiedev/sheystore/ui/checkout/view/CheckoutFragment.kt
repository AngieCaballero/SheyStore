package com.angiedev.sheystore.ui.checkout.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentCheckoutBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.checkout.adapter.OrderListAdapter
import com.angiedev.sheystore.ui.checkout.viewmodel.CheckoutViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>() {

    private val checkoutViewModel: CheckoutViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()

    private var orderListAdapter: OrderListAdapter? = null
    override fun getViewBinding() = FragmentCheckoutBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupOrderListAdapter()
        checkoutViewModel.getOrderList(userDataViewModel.readValue(PreferencesKeys.EMAIL).orEmpty())
        checkoutViewModel.getShippingAddress(userDataViewModel.readValue(PreferencesKeys.EMAIL).orEmpty())
    }

    private fun setupOrderListAdapter() {
        orderListAdapter = OrderListAdapter()
        binding.fragmentCheckoutOrderList.adapter = orderListAdapter
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentCheckoutShippingAddress.itemShippingAddressNoData.setOnClickListener {
                // Navigate To Add Shipping Address
                Toast.makeText(requireContext(), "Go to Add Shipping Address", Toast.LENGTH_SHORT).show()
            }

            binding.fragmentCheckoutToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        checkoutViewModel.shippingAddress.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    setShippingAddress(it.data)
                }
            }
        }

        checkoutViewModel.orderList.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    orderListAdapter?.submitList(it.data)
                }
            }
        }
    }

    private fun setShippingAddress(data: List<ShippingAddressEntity>) {
        with(binding.fragmentCheckoutShippingAddress) {
            val defaultShippingAddress = data.firstOrNull { it.default }
            if (defaultShippingAddress != null) {
                itemShippingAddressData.setVisible()
                itemShippingAddressNoData.setGone()
                itemShippingAddressName.text = defaultShippingAddress.name
                itemShippingAddressDetails.text = defaultShippingAddress.details
            } else {
                itemShippingAddressNoData.setVisible()
                itemShippingAddressData.setGone()
            }
        }
    }

}
package com.angiedev.sheystore.ui.checkout.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
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

    private var shippingAddressIsEmpty = false
    private var orderListAdapter: OrderListAdapter? = null
    override fun getViewBinding() = FragmentCheckoutBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupOrderListAdapter()
        val userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        checkoutViewModel.getOrderList(userId)
        checkoutViewModel.getShippingAddress(userId)
    }

    private fun setupOrderListAdapter() {
        orderListAdapter = OrderListAdapter()
        binding.fragmentCheckoutOrderList.adapter = orderListAdapter
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentCheckoutShippingAddress.itemShippingAddressNoData.setOnClickListener {
                if (shippingAddressIsEmpty) {
                    Toast.makeText(requireContext(), "Go to Add Shipping Address", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToChoiceMyShippingAddressFragment())
                }
            }

            binding.fragmentCheckoutToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            fragmentCheckoutShippingAddress.itemShippingAddressData.setOnClickListener {
                findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToChoiceMyShippingAddressFragment())
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        checkoutViewModel.shippingAddress.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> {
                    setShippingAddress(emptyList())
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
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
                    orderListAdapter?.submitList(it.data.cartItems)
                }
            }
        }
    }

    private fun setShippingAddress(data: List<ShippingAddressEntity>) {
        with(binding.fragmentCheckoutShippingAddress) {
            if(data.isEmpty()) {
                shippingAddressIsEmpty = true
                itemShippingAddressDataMessage.text = getString(com.angiedev.sheystore.R.string.ooops_al_parecer_no_tienes_direcciones_de_entrega)
                itemShippingAddressNoData.setVisible()
                itemShippingAddressData.setGone()
            } else {
                val defaultShippingAddress = data.firstOrNull { it.default }
                if (defaultShippingAddress != null) {
                    itemShippingAddressData.setVisible()
                    itemShippingAddressNoData.setGone()
                    itemShippingAddressName.text = defaultShippingAddress.name
                    itemShippingAddressDetails.text = defaultShippingAddress.details
                } else {
                    shippingAddressIsEmpty = false
                    itemShippingAddressDataMessage.text = getString(com.angiedev.sheystore.R.string.default_shipping_address_not_default_selected)
                    itemShippingAddressNoData.setVisible()
                    itemShippingAddressData.setGone()
                }
            }
        }
    }

}
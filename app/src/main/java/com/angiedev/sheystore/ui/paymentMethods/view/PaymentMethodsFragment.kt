package com.angiedev.sheystore.ui.paymentMethods.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentPaymentMethodsBinding
import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.paymentMethods.adapter.PaymentMethodsAdapter
import com.angiedev.sheystore.ui.paymentMethods.adapter.PaymentMethodsListener
import com.angiedev.sheystore.ui.paymentMethods.viewmodel.PaymentMethodsViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMethodsFragment : BaseFragment<FragmentPaymentMethodsBinding>(), PaymentMethodsListener {

    override var isBottomNavVisible = View.GONE
    private var paymentMethodSelected: PaymentMethodEntity? = null
    private val userDataViewModel: UserDataViewModel by viewModels()
    private val paymentMethodsViewModel: PaymentMethodsViewModel by viewModels()
    private var paymentMethodsAdapter: PaymentMethodsAdapter? = null
    override fun getViewBinding() =  FragmentPaymentMethodsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupPaymentAdapter()
        paymentMethodsViewModel.getPaymentMethods(
            userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        )
    }

    override fun setObservers() {
        super.setObservers()
        paymentMethodsViewModel.paymentMethods.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    paymentMethodsAdapter?.submitList(it.data)
                }
            }
        }
    }
    override fun setListeners() {
        super.setListeners()
        with(binding) {
            paymentMethodsButtonPaymentConfirm.setOnClickListener {
                if (paymentMethodSelected != null) {
                    // Go to next screen
                } else {
                    Toast.makeText(requireContext(), "Selecciona un método de pago", Toast.LENGTH_SHORT).show()
                }
            }

            paymentMethodsAddNewCard.setOnClickListener {
                findNavController().navigate(PaymentMethodsFragmentDirections.actionPaymentMethodsFragmentToAddNewCardFragment())
            }
        }
    }

    private fun setupPaymentAdapter() {
        paymentMethodsAdapter = PaymentMethodsAdapter(this)
        binding.paymentMethodsCards.adapter = paymentMethodsAdapter
    }

    override fun onCheckedItemListener(paymentMethodEntity: PaymentMethodEntity) {
        paymentMethodSelected = paymentMethodEntity
    }

}
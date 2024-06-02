package com.angiedev.sheystore.ui.paymentMethods.view

import android.os.Bundle
import android.view.View
import com.angiedev.sheystore.databinding.FragmentPaymentMethodsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.paymentMethods.adapter.PaymentMethodsAdapter

class PaymentMethodsFragment : BaseFragment<FragmentPaymentMethodsBinding>() {

    private var paymentMethodsAdapter: PaymentMethodsAdapter? = null
    override fun getViewBinding() =  FragmentPaymentMethodsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupPaymentAdapter()
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            paymentMethodsButtonAdd.setOnClickListener {

            }
        }
    }

    private fun setupPaymentAdapter() {
        paymentMethodsAdapter = PaymentMethodsAdapter()
        binding.paymentMethodsCards.adapter = paymentMethodsAdapter
    }

}
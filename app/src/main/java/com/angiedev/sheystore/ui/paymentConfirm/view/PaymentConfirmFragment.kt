package com.angiedev.sheystore.ui.paymentConfirm.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angiedev.sheystore.databinding.FragmentPaymentConfirmBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class PaymentConfirmFragment : BaseFragment<FragmentPaymentConfirmBinding>() {

    override var isBottomNavVisible = View.GONE
    private val args: PaymentConfirmFragmentArgs by navArgs()

    override fun getViewBinding() = FragmentPaymentConfirmBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            paymentConfirmToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            paymentConfirmContinueButton.setOnClickListener {
                if (args.cvc == paymentConfirmCvc.text.toString()){
                    // Show Dialog
                } else {
                    Toast.makeText(requireContext(), "CVC incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
    }
}
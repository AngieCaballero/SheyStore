package com.angiedev.sheystore.ui.paymentConfirm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.angiedev.sheystore.databinding.FragmentPaymentConfirmDialogBinding

class PaymentConfirmDialog : DialogFragment() {

    private var _binding: FragmentPaymentConfirmDialogBinding? = null
    private val binding get() = _binding!!

    private var paymentConfirmListener: PaymentConfirmListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_MinWidth);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentConfirmDialogBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    fun setPaymentConfirmListener(listener: PaymentConfirmListener) {
        paymentConfirmListener = listener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setListener()
    }

    private fun setListener() {
        with(binding) {
            paymentConfirmDialogOrder.setOnClickListener {
                paymentConfirmListener?.goToOrder()
                dismiss()
            }

            paymentConfirmDialogShopping.setOnClickListener {
                paymentConfirmListener?.gotToShopping()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        paymentConfirmListener?.gotToShopping()
        super.onDestroyView()
    }

    companion object {
        const val TAG = "PaymentConfirmDialog"
        fun newInstance() = PaymentConfirmDialog()
    }
}
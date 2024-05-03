package com.angiedev.sheystore.ui.cart

import android.os.Bundle
import android.view.View
import com.angiedev.sheystore.databinding.BottomSheetRemoveCartItemDialogBinding
import com.angiedev.sheystore.ui.base.BaseBottomSheetDialogFragment
import com.angiedev.sheystore.ui.utils.extension.setGone

class RemoveCartItemBottomSheetDialog : BaseBottomSheetDialogFragment<BottomSheetRemoveCartItemDialogBinding>() {
    override fun getViewBinding() = BottomSheetRemoveCartItemDialogBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupCartItem()
    }

    private fun setupCartItem() {
        binding.bottomSheetItemCart.itemCartRemoveIcon.setGone()
        binding.bottomSheetItemCart.itemCartQuantityStepper.setGone()
    }

    override fun setListeners() {
        super.setListeners()
        binding.bottomSheetCancel.setOnClickListener {
            dismiss()
        }

        binding.bottomSheetRemove.setOnClickListener {
            
        }
    }

    companion object {
        fun newInstance() = RemoveCartItemBottomSheetDialog()
    }

}
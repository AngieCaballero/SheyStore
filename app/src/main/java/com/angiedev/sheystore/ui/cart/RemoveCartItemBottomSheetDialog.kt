package com.angiedev.sheystore.ui.cart

import android.os.Bundle
import android.view.View
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.CartEntity
import com.angiedev.sheystore.databinding.BottomSheetRemoveCartItemDialogBinding
import com.angiedev.sheystore.ui.base.BaseBottomSheetDialogFragment
import com.angiedev.sheystore.ui.utils.extension.parcelable
import com.angiedev.sheystore.ui.utils.extension.setGone
import com.angiedev.sheystore.ui.utils.extension.setInvisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RemoveCartItemBottomSheetDialog : BaseBottomSheetDialogFragment<BottomSheetRemoveCartItemDialogBinding>() {

    private var cartItem: CartEntity? = null
    private var onRemoveCartItem: (() -> Unit)? = null

    override fun getViewBinding() = BottomSheetRemoveCartItemDialogBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupCartItem()
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        cartItem = args?.parcelable(CART_ITEM)
    }

    fun setOnRemoveCartItemListener(onRemoveCartItem: () -> Unit) {
        this.onRemoveCartItem = onRemoveCartItem
    }

    private fun setupCartItem() {
        with(binding.bottomSheetItemCart) {
            itemCartRemoveIcon.setInvisible()
            itemCartName.text = cartItem?.name
            itemCartTotalPrice.text = cartItem?.price.toString()
            itemCartQuantityStepper.value = cartItem?.quantity?.toIntOrNull() ?: 0

            Glide.with(root.context)
                .load(cartItem?.image.orEmpty())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.product_hint)
                .into(itemCartImage)
        }

    }

    override fun setListeners() {
        super.setListeners()
        binding.bottomSheetCancel.setOnClickListener {
            dismiss()
        }

        binding.bottomSheetRemove.setOnClickListener {
            onRemoveCartItem?.invoke()
            dismiss()
        }
    }

    companion object {
        const val CART_ITEM = "cartItem"
        fun newInstance(bundle: Bundle) = RemoveCartItemBottomSheetDialog().apply {
            arguments = bundle
        }
    }

}
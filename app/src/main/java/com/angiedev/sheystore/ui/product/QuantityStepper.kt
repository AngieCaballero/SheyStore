package com.angiedev.sheystore.ui.product

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.angiedev.sheystore.databinding.QuantityStepperBinding

class QuantityStepper @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {


    private val binding =
        QuantityStepperBinding.inflate(LayoutInflater.from(context), this, true)
    private var currentValue: Int = 1
    private var listener: QuantityStepperListener? = null

    var value: Int
        get() = currentValue
        set(value) {
            currentValue = value
            binding.quantityEt.text = Editable.Factory.getInstance().newEditable(value.toString())
        }


    fun setQuantityStepperListener(listener: QuantityStepperListener?) {
        this.listener = listener
    }


    init {

        binding.decreaseIb.setOnClickListener {
            doDec()
        }

        binding.increaseIb.setOnClickListener {
            doInc()
        }
    }

    fun setControlsVisibility(visibility: Int) {
        binding.decreaseIb.visibility = visibility
        binding.increaseIb.visibility = visibility
    }

    private fun doDec() {
        if (currentValue > 0) {
            val decreasedValue: Int = currentValue.dec()
            currentValue = decreasedValue
            binding.quantityEt.text = currentValue.toString()
            listener?.onValueChanged(currentValue)
        }
    }


    private fun doInc() {
        val increasedValue: Int = currentValue.inc()
        currentValue = increasedValue
        binding.quantityEt.text = currentValue.toString()
        listener?.onValueChanged(currentValue)
    }

}
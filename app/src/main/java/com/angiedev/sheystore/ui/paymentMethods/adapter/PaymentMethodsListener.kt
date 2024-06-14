package com.angiedev.sheystore.ui.paymentMethods.adapter

import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity

interface PaymentMethodsListener {
    fun onCheckedItemListener(paymentMethodEntity: PaymentMethodEntity)
}
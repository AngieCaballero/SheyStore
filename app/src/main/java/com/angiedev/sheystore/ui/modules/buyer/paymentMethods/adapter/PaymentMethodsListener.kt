package com.angiedev.sheystore.ui.modules.buyer.paymentMethods.adapter

import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity

interface PaymentMethodsListener {
    fun onCheckedItemListener(paymentMethodEntity: PaymentMethodEntity)
}
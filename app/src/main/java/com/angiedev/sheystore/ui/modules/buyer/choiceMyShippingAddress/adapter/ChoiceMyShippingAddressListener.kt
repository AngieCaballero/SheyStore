package com.angiedev.sheystore.ui.modules.buyer.choiceMyShippingAddress.adapter

import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity

interface ChoiceMyShippingAddressListener {

    fun onCheckedItemListener(shippingAddressEntity: ShippingAddressEntity)
}
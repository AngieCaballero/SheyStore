package com.angiedev.sheystore.ui.choiceMyShippingAddress.adapter

import com.angiedev.sheystore.data.entities.ShippingAddressEntity

interface ChoiceMyShippingAddressListener {

    fun onCheckedItemListener(shippingAddressEntity: ShippingAddressEntity)
}
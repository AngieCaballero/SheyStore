package com.angiedev.sheystore.ui.modules.buyer.product.adapter

import com.angiedev.sheystore.domain.entities.product.ProductEntity

interface ProductItemListener {

    fun onClickItem(productEntity: ProductEntity)
}
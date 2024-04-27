package com.angiedev.sheystore.ui.product.adapter

import com.angiedev.sheystore.data.entities.ProductEntity

interface ProductItemListener {

    fun onClickItem(productEntity: ProductEntity)
}
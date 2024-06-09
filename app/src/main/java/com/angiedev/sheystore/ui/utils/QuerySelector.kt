package com.angiedev.sheystore.ui.utils

import com.angiedev.sheystore.data.model.domain.entities.product.ProductEntity

class QuerySelector {
    data class Builder (
        var category: String = "All",
        var rating: String = "All",
        var minPrice: String = "0.0",
        var maxPrice: String = "0.0",
        var listToFilter: List<ProductEntity> = emptyList()
    ) {
        fun category(category: String) = apply { this.category = category }
        fun rating(rating: String) = apply { this.rating = rating }
        fun minPrice(minPrice: String) = apply { this.minPrice = minPrice }
        fun maxPrice(maxPrice: String) = apply { this.maxPrice = maxPrice }
        fun setListToFilter(listToFilter: List<ProductEntity>) = apply { this.listToFilter = listToFilter }

        fun build() : List<ProductEntity> {
            val newList = listToFilter.filter {
                it.category.name.contains(category) || it.rate.contains(rating) || it.price >= minPrice.toDouble() && it.price <= maxPrice.toDouble()
            }
            return newList
        }

    }
}
package com.angiedev.sheystore.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.CategoryEntity
import com.angiedev.sheystore.databinding.BottomSheetSortFilterDialogBinding
import com.angiedev.sheystore.databinding.ItemCategoryChipsBinding
import com.angiedev.sheystore.ui.base.BaseBottomSheetDialogFragment
import com.angiedev.sheystore.ui.utils.extension.parcelableList
import com.google.android.material.chip.Chip
import java.text.NumberFormat
import java.util.Currency

class SortFilterBottomSheetDialog : BaseBottomSheetDialogFragment<BottomSheetSortFilterDialogBinding>() {

    private var categories = listOf<CategoryEntity>()
    private val rating = listOf(
        "All", "5", "4", "3", "2", "1"
    )
    private var minPrice = 0f
    private var maxPrice = 0f
    override fun getViewBinding() = BottomSheetSortFilterDialogBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupSortFilter()
        setupMostPopularCategoryChips()
        setupPriceSlider()
        setupRatingChips()
    }

    private fun setupRatingChips() {
        with(binding.sortFilterChipsGroupRating) {
            if (childCount > 0) return@with
            rating.forEachIndexed { index, item ->
                val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
                chip.root.apply {
                    text = item
                    id = index
                    isCheckedIconVisible = true
                    checkedIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_rating_checked)
                    chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_rating)
                }
                addView(chip.root)
            }
            check(getChildAt(0).id)
        }
    }

    private fun setupPriceSlider() {
        with(binding.sortFilterRangeSlider) {
            valueFrom = minPrice
            valueTo = maxPrice
            values = listOf(minPrice, maxPrice - 20)
            setLabelFormatter { value ->
                val format = NumberFormat.getCurrencyInstance()
                format.maximumFractionDigits = 2
                format.currency = Currency.getInstance("USD")
                return@setLabelFormatter format.format(value.toDouble())
            }
        }
    }


    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        args?.let {
            categories = it.parcelableList(CATEGORIES) ?: emptyList()
            maxPrice = it.getDouble(MAX_PRICE).toFloat()
            minPrice = it.getDouble(MIN_PRICE).toFloat()
        }
    }

    private fun setupSortFilter() {
    }

    override fun setListeners() {
        super.setListeners()
        binding.sortFilterChipsGroupCategories.setOnCheckedStateChangeListener { chipGroup, ints ->
            // Event Chip Checked
            val selectedChip = chipGroup.findViewById<Chip>(ints.first())
        }
    }

    private fun setupMostPopularCategoryChips() {
        with(binding.sortFilterChipsGroupCategories) {
            if (childCount > 0) return@with
            categories.forEachIndexed { index, item ->
                val chip = ItemCategoryChipsBinding.inflate(layoutInflater)
                chip.root.apply {
                    isCheckedIconVisible = false
                    text = item.name
                    id = index
                }
                addView(chip.root)
            }
            check(getChildAt(0).id)
        }
    }

    companion object {
        const val TAG = "SortFilterBottomSheetDialog"
        const val CATEGORIES = "categories"
        const val MAX_PRICE = "maxPrice"
        const val MIN_PRICE = "minPrice"
        fun newInstance(bundle: Bundle) = SortFilterBottomSheetDialog().apply {
            arguments = bundle
        }
    }
}
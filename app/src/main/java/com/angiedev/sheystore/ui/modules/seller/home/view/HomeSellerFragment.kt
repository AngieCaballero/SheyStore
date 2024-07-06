package com.angiedev.sheystore.ui.modules.seller.home.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Toast
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentHomeSellerBinding
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.seller.home.adapter.IncomeAdapter
import com.angiedev.sheystore.ui.modules.seller.home.adapter.ProductSoldQuantityAdapter
import com.angiedev.sheystore.ui.modules.seller.home.viewmodel.HomeSellerViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.CustomMarkerComponent
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.extension.sumOf
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeSellerFragment : BaseFragment<FragmentHomeSellerBinding>() {

    private val viewModel: HomeSellerViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private var userId = 0

    private var productSoldQuantityAdapter: ProductSoldQuantityAdapter? = null
    private var incomeAdapter: IncomeAdapter? = null


    override fun getViewBinding() = FragmentHomeSellerBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        viewModel.getTopCategories(
            userId
        )
        viewModel.getProductSoldQuantity(userId)
        viewModel.getIncome(userId)
        setupAdapters()
        setupChart()
    }

    private fun setupAdapters() {
        productSoldQuantityAdapter = ProductSoldQuantityAdapter()
        binding.fragmentHomeSellerProductCountRecycler.adapter = productSoldQuantityAdapter

        incomeAdapter = IncomeAdapter()
        binding.fragmentHomeSellerIncomeRecycler.adapter = incomeAdapter
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentHomeSellerTopCategoriesExport.setOnClickListener {
                viewModel.downloadTopCategoriesReport(userId)
            }

            fragmentHomeSellerIncomeExport.setOnClickListener {
                viewModel.downloadIncomeReport(userId)
            }

            fragmentHomeSellerProductCountExport.setOnClickListener {
                viewModel.downloadProductsSoldQuantityReport(userId)
            }
        }
    }

    private fun setupChart() {
        with(binding.fragmentHomeSellerChart) {
            marker = CustomMarkerComponent(
                label = TextComponent.Builder().apply {
                    lineCount = 3
                    padding = dimensionsOf(6.dp, 4.dp)
                    color = resources.getColor(R.color.teal_700, resources.newTheme())
                    typeface = Typeface.SANS_SERIF
                    textAlignment = Layout.Alignment.ALIGN_CENTER
                }.build(),
                guideline = LineComponent(
                    color = resources.getColor(R.color.teal_700, resources.newTheme()),
                    shape = DashedShape(Shapes.pillShape, 8f, 4f)
                ),
                indicator = null
            )
            chart = ColumnChart(
                columns = listOf(
                    LineComponent(
                        color = resources.getColor(R.color.teal_700, resources.newTheme()),
                        thicknessDp = 6f,
                        shape = Shapes.roundedCornerShape(
                            topLeftPercent = 10,
                            topRightPercent = 10
                        )
                    )
                )
            )
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.topCategories.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {}
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    buildChart(it.data)
                }
            }
        }

        viewModel.productSoldQuantity.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {}
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    productSoldQuantityAdapter?.submitList(it.data)
                }
            }
        }

        viewModel.income.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {}
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    incomeAdapter?.submitList(it.data)
                }
            }
        }

        viewModel.downloadFile.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildChart(data: List<TopCategoriesEntity>) {
        with(binding.fragmentHomeSellerChart) {
            bottomAxis = HorizontalAxis(AxisPosition.Horizontal.Bottom).apply {
                valueFormatter = AxisValueFormatter { value, _ ->
                    data.getOrNull(value.roundToInt())?.category?.first.orEmpty()
                }
                itemPlacer = AxisItemPlacer.Horizontal.default(
                    offset = 1,
                    spacing = 1,
                    shiftExtremeTicks = false,
                    addExtremeLabelPadding = true
                )
                axisLine = LineComponent(
                    color = resources.getColor(R.color.color_primary_variant, resources.newTheme()),
                    thicknessDp = 1F
                )
                label = TextComponent.Builder().apply {
                    color = resources.getColor(R.color.color_text, resources.newTheme())

                }.build()
            }
            startAxis = VerticalAxis(AxisPosition.Vertical.Start).apply {
                valueFormatter = AxisValueFormatter { value, _ ->
                    value.roundToInt().toString()
                }
                itemPlacer = AxisItemPlacer.Vertical.default(
                    maxItemCount = 6
                )
                axisLine = LineComponent(
                    color = resources.getColor(R.color.color_primary_variant, resources.newTheme()),
                    thicknessDp = 1F
                )

                guideline = LineComponent(
                    color = resources.getColor(R.color.color_primary_variant, resources.newTheme()),
                    thicknessDp = 1F
                )

                label = TextComponent.Builder().apply {
                    color = resources.getColor(R.color.color_text, resources.newTheme())
                    margins = MutableDimensions(
                        endDp = 6f,
                        bottomDp = 6f,
                        startDp = 6f,
                        topDp = 6f
                    )
                }.build()
            }

            entryProducer = ChartEntryModelProducer(
                data.map {
                    entryOf(
                        it.category.second.toFloat(),
                        it.totalQuantity.toFloat()
                    )
                }
            )

            chart?.axisValuesOverrider = AxisValuesOverrider.fixed(
                minX = 0F,
                maxX = data.size.toFloat() - 1,
                minY = 0F,
                maxY = data.sumOf { it.totalQuantity.toFloat() },
            )
        }
    }
}
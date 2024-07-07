package com.angiedev.sheystore.ui.modules.admin.home.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Toast
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentAdminHomeBinding
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.admin.home.adapter.UsersReportAdapter
import com.angiedev.sheystore.ui.modules.admin.home.viewmodel.AdminHomeViewModel
import com.angiedev.sheystore.ui.utils.CustomMarkerComponent
import com.angiedev.sheystore.ui.utils.helper.getFormattedDate
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
class AdminHomeFragment : BaseFragment<FragmentAdminHomeBinding>() {

    override var isBottomNavVisible = View.VISIBLE
    private val viewModel: AdminHomeViewModel by viewModels()

    private var usersReportAdapter: UsersReportAdapter? = null
    override fun getViewBinding(): FragmentAdminHomeBinding =
        FragmentAdminHomeBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupChart()
        setupAdapter()
        viewModel.getUsersReport()
        viewModel.getProductSoldGlobalQuantity()
    }

    private fun setupAdapter() {
        usersReportAdapter = UsersReportAdapter()
        binding.adminHomeUsersReportRecycler.adapter = usersReportAdapter
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            adminHomeProductExport.setOnClickListener {
                viewModel.downloadProductSoldGlobalReport()
            }
            adminHomeUsersReportExport.setOnClickListener {
                viewModel.downloadUsersReport()
            }
        }
    }

    private fun setupChart() {
        with(binding.adminHomeChart) {
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
        viewModel.productSoldGlobalQuantity.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> {}
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    buildChart(it.data)
                }
            }
        }

        viewModel.usersReport.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> {}
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    usersReportAdapter?.submitList(it.data)
                }
            }
        }

        viewModel.downloadFile.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildChart(data: List<ProductSoldQuantityEntity>) {
        with(binding.adminHomeChart) {
            bottomAxis = HorizontalAxis(AxisPosition.Horizontal.Bottom).apply {
                valueFormatter = AxisValueFormatter { value, _ ->
                    data.getOrNull(value.roundToInt())?.date?.first?.getFormattedDate("MMM dd").orEmpty().replaceFirstChar(Char::titlecase)
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
                        it.date.second,
                        it.totalQuantity.first
                    )
                }
            )

            chart?.axisValuesOverrider = AxisValuesOverrider.fixed(
                minX = 0F,
                maxX = data.size.toFloat() - 1,
                minY = 0F,
                maxY = data.sumOf { it.totalQuantity.second.toFloat() },
            )
        }
    }
}
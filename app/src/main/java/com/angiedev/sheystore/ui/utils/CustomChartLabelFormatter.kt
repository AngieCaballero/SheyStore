package com.angiedev.sheystore.ui.utils

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.extension.appendCompat
import com.patrykandpatrick.vico.core.extension.transformToSpannable
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter
import kotlin.math.roundToInt

class CustomChartLabelFormatter(
    private val colorCode: Boolean = true
) : MarkerLabelFormatter {

    override fun getLabel(
        markedEntries: List<Marker.EntryModel>,
        chartValues: ChartValues
    ): CharSequence = markedEntries.transformToSpannable(
        prefix = "",
        postfix = "",
        separator = "\n",
    ) { model ->
        if (colorCode) {
            appendCompat(
                model.entry.y.roundToInt().toString(),
                ForegroundColorSpan(Color.White.toArgb()),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        } else {
            append(PATTERN.format(model.entry.y))
        }
    }

    private companion object {
        const val PATTERN = "$%.02f"
    }


}
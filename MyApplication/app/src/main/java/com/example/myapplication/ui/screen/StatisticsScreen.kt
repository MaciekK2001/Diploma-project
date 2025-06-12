package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import com.example.myapplication.dtos.DailyStatisticsDTO
import com.example.myapplication.dtos.MonthlyStatisticsDTO
import com.example.myapplication.ui.theme.Purple200

@Composable
fun DailyStatisticsLineChart(data: List<DailyStatisticsDTO>) {
    val pointsData: List<Point> = data.mapIndexed { index, dto ->
        Point(x = index.toFloat(), y = dto.sumOfCalories.toFloat())
    }

    val yAxisSteps = 7

    val xAxisData = AxisData.Builder()
        .axisStepSize(75.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            when (i) {
                0 -> "                    ${data.first().date}"
                pointsData.size - 1 -> "  ${data.last().date}                    "
                else -> ""
            }
        }
        .labelAndAxisLinePadding(15.dp)
        .backgroundColor(Color.Transparent)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(yAxisSteps)
        .labelAndAxisLinePadding(40.dp)
        .backgroundColor(Color.Transparent)
        .labelData { i ->
            val yScale = data.maxOfOrNull { it.sumOfCalories }?.toFloat()?.div(yAxisSteps) ?: 0f
            (i * yScale).formatToSinglePrecision()
        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(color = Purple200),
                    intersectionPoint = IntersectionPoint(color = Color.Black, radius = 4.dp),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        backgroundColor = Color.Gray
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun DailyStatisticsLineChartWithTitles(data: List<DailyStatisticsDTO>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.material3.Text(
                text = "Weekly statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(onClick = {}) { Text(text = "change to monthly", color = Color.White) }
            Spacer(modifier = Modifier.padding(2.dp))

            DailyStatisticsLineChart(data = data)

            Text(
                text = "x - date, y - burnt calories",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        }
    }
}



@Composable
fun YearlyStatisticsLineChart(data: List<MonthlyStatisticsDTO>) {
    val pointsData: List<Point> = data.mapIndexed { index, dto ->
        Point(x = index.toFloat(), y = dto.sumOfCalories.toFloat())
    }

    val yAxisSteps = 7

    val xAxisData = AxisData.Builder()
        .axisStepSize(75.dp)
        .steps(pointsData.size - 1)
        .labelData { i ->
            when (i) {
                0 -> "                    ${data.first().yearMonth}"
                pointsData.size - 1 -> "  ${data.last().yearMonth}                    "
                else -> ""
            }
        }
        .labelAndAxisLinePadding(15.dp)
        .backgroundColor(Color.Transparent)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(yAxisSteps)
        .labelAndAxisLinePadding(40.dp)
        .backgroundColor(Color.Transparent)
        .labelData { i ->
            val yScale = data.maxOfOrNull { it.sumOfCalories }?.toFloat()?.div(yAxisSteps) ?: 0f
            (i * yScale).formatToSinglePrecision()
        }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(color = Purple200),
                    intersectionPoint = IntersectionPoint(color = Color.Black, radius = 4.dp),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        backgroundColor = Color.Gray
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}


@Composable
@Preview
fun PreviewChart() {
    val sampleData = listOf(
        DailyStatisticsDTO("2025-01-10", 2000),
        DailyStatisticsDTO("2025-01-11", 2200),
        DailyStatisticsDTO("2025-01-12", 2100),
        DailyStatisticsDTO("2025-01-13", 2500),
        DailyStatisticsDTO("2025-01-14", 2300)
    )
    DailyStatisticsLineChart(data = sampleData)
}

@Composable
@Preview
fun PreviewChartWithTitles() {
    val sampleData = listOf(
        DailyStatisticsDTO("2025-01-10", 2000),
        DailyStatisticsDTO("2025-01-11", 2200),
        DailyStatisticsDTO("2025-01-12", 2100),
        DailyStatisticsDTO("2025-01-13", 2500),
        DailyStatisticsDTO("2025-01-14", 2300)
    )
    DailyStatisticsLineChartWithTitles(data = sampleData)
}

@Composable
fun YearlyStatisticsLineChartWithTitles(data: List<MonthlyStatisticsDTO>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.material3.Text(
                text = "Yearly statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            YearlyStatisticsLineChart(data = data)

            Text(
                text = "x - months, y - burnt calories",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        }
    }
}

@Composable
@Preview
fun PreviewYearlyStatisticsLineChartWithTitles() {
    val sampleData = listOf(
        MonthlyStatisticsDTO("2025-01", 60000),
        MonthlyStatisticsDTO("2025-02", 65000),
        MonthlyStatisticsDTO("2025-03", 70000),
        MonthlyStatisticsDTO("2025-04", 62000),
        MonthlyStatisticsDTO("2025-05", 68000)
    )
    YearlyStatisticsLineChartWithTitles(data = sampleData)
}

@Composable
@Preview
fun combined(){
    val sampleDataYearly = listOf(
        MonthlyStatisticsDTO("2024-01", 66000),
        MonthlyStatisticsDTO("2025-02", 65000),
        MonthlyStatisticsDTO("2025-03", 70000),
        MonthlyStatisticsDTO("2025-04", 65000),
        MonthlyStatisticsDTO("2025-05", 68000),
        MonthlyStatisticsDTO("2025-06", 62000),
        MonthlyStatisticsDTO("2025-07", 65000),
    )

    val sampleDataWeekly = listOf(
        DailyStatisticsDTO("2025-01-10", 2000),
        DailyStatisticsDTO("2025-01-11", 2200),
        DailyStatisticsDTO("2025-01-12", 2100),
        DailyStatisticsDTO("2025-01-13", 2500),
        DailyStatisticsDTO("2025-01-14", 2300),
        DailyStatisticsDTO("2025-01-15", 1890),
        DailyStatisticsDTO("2025-01-16", 1900)
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DailyStatisticsLineChartWithTitles(sampleDataWeekly)
        Spacer(modifier = Modifier.padding(10.dp))
        YearlyStatisticsLineChartWithTitles(sampleDataYearly)
    }

}




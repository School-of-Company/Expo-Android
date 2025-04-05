package com.school_of_company.expo.view.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ExpoColor

data class SchoolData(
    val elementary: SchoolCategory,
    val middle: SchoolCategory,
    val high: SchoolCategory,
    val kindergarten: SchoolCategory,
    val other: SchoolCategory
)

data class SchoolCategory(
    val number: Int,
    val percent: Int
)

@Composable
internal fun ParticipantPieChart(
    data: SchoolData,
    radiusOuter: Dp = 160.dp,
    chartBarWidth: Dp = 50.dp,
) {
    ExpoAndroidTheme { colors, _ ->

        val categoryList = listOf(
            "유치원" to data.kindergarten,
            "초등학교" to data.elementary,
            "중학교" to data.middle,
            "고등학교" to data.high,
            "기타" to data.other
        )

        val totalSum = categoryList.sumOf { it.second.number }
        val floatValues = categoryList.map {
            360f * it.second.number / totalSum.toFloat()
        }

        val colors = listOf(
            colors.main600,
            colors.main400,
            colors.main500,
            colors.main200,
            colors.main100
        )

        var animationPlayed by remember { mutableStateOf(false) }
        var lastValue = 0f

        val animateSize by animateFloatAsState(
            targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )

        val animateRotation by animateFloatAsState(
            targetValue = if (animationPlayed) 990f else 0f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )

        LaunchedEffect(true) {
            animationPlayed = true
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            DetailsPieChart(
                data = data,
                colors = colors
            )

            Spacer(modifier = Modifier.padding(bottom = 81.dp))

            Box(modifier = Modifier.size(animateSize.dp)) {
                Canvas(
                    modifier = Modifier
                        .size(radiusOuter * 2f)
                        .rotate(animateRotation)
                ) {
                    floatValues.forEachIndexed { index, value ->
                        drawArc(
                            color = colors[index % colors.size],
                            startAngle = lastValue,
                            sweepAngle = value,
                            useCenter = false,
                            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                        )
                        lastValue += value
                    }
                }
            }

            Spacer(modifier = Modifier.padding(36.dp))
        }
    }
}

@Composable
private fun DetailsPieChart(
    data: SchoolData,
    colors: List<Color>
) {
    val items = listOf(
        "유치원" to data.kindergarten,
        "초등학교" to data.elementary,
        "중학교" to data.middle,
        "고등학교" to data.high,
        "기타" to data.other
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(90.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items) { index, (label, category) ->
            DetailsPieChartItem(
                label = label,
                value = category.number,
                percent = category.percent,
                color = colors.getOrElse(index) { ExpoColor.gray400 }
            )
        }
    }
}

@Composable
private fun DetailsPieChartItem(
    label: String,
    value: Int,
    percent: Int,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = CircleShape
                )
                .size(16.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = label,
                style = ExpoTypography.captionBold1,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = "${value}명 ($percent%)",
                style = ExpoTypography.captionRegular2,
                color = ExpoColor.gray400
            )
        }
    }
}


@Preview
@Composable
private fun ParticipantPieChartPreview() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ParticipantPieChart(
            data = SchoolData(
                kindergarten = SchoolCategory(number = 10, percent = 10),
                elementary = SchoolCategory(number = 30, percent = 30),
                middle = SchoolCategory(number = 20, percent = 20),
                high = SchoolCategory(number = 30, percent = 30),
                other = SchoolCategory(number = 10, percent = 10),
            )
        )
    }
}
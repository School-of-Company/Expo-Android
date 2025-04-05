package com.school_of_company.expo.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
internal fun DetailsPieChart(
    data: SchoolData
) {
    ExpoAndroidTheme { colors, typography ->

        val items = listOf(
            "유치원" to data.kindergarten,
            "초등학교" to data.elementary,
            "중학교" to data.middle,
            "고등학교" to data.high,
            "기타" to data.other
        )

        val colors = listOf(
            colors.main600,
            colors.main400,
            colors.main500,
            colors.main200,
            colors.main100
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
}
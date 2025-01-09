package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme

@Composable
fun ExpoCreatedTable(modifier: Modifier = Modifier) {
    ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(73.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(color = colors.white)
                .drawBehind {
                    // 아래쪽 Border 그리기
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = colors.gray100, // Border 색상
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(
                    horizontal = 54.dp,
                    vertical = 14.dp,
                )
        ) {
            Text(
                text = "이미지",
                style = typography.captionBold1,
                fontWeight = FontWeight.W600,
                color = colors.gray600,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "박람회 이름",
                style = typography.captionBold1,
                fontWeight = FontWeight.W600,
                color = colors.gray600,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "모집 기간",
                style = typography.captionBold1,
                fontWeight = FontWeight.W600,
                color = colors.gray600,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview
@Composable
fun ExpoCreatedTablePreview() {
    ExpoCreatedTable()
}
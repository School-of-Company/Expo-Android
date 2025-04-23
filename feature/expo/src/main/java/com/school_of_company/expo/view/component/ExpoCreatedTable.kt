package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoCreatedTable(modifier: Modifier = Modifier) {
    ExpoAndroidTheme { colors, typography ->
        Column(Modifier.fillMaxWidth()) {
            HorizontalDivider(thickness = 1.dp, color = colors.gray200)
            Row(
                horizontalArrangement = Arrangement.spacedBy(73.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = colors.white)
                    .padding(
                        horizontal = 90.dp,
                        vertical = 14.dp,
                    )
            ) {
                Text(
                    modifier = Modifier.requiredWidthIn(80.dp),
                    text = "박람회 이름",
                    style = typography.captionBold1,
                    fontWeight = FontWeight.W600,
                    color = colors.gray600,
                )

                Text(
                    modifier = Modifier.requiredWidthIn(52.dp),
                    text = "모집 기간",
                    style = typography.captionBold1,
                    fontWeight = FontWeight.W600,
                    color = colors.gray600,
                    textAlign = TextAlign.Center,
                )
            }
            HorizontalDivider(thickness = 1.dp, color = colors.gray100)
        }
    }
}


@Preview
@Composable
fun ExpoCreatedTablePreview() {
    ExpoCreatedTable()
}
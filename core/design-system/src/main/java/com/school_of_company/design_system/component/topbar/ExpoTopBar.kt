package com.school_of_company.design_system.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.text.ExpoSubjectTitleText
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoTopBar(
    modifier: Modifier = Modifier,
    startIcon: @Composable () -> Unit,
    betweenText: String = "",
    endIcon: @Composable () -> Unit = { Spacer(modifier = modifier.size(24.dp)) }
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 15.dp
                )
        ) {
            startIcon()
            ExpoSubjectTitleText(subjectText = betweenText)
            endIcon()
        }
    }
}

@Preview
@Composable
private fun ExpoTopBarPreview() {
    ExpoTopBar(
        startIcon = { LeftArrowIcon() },
        betweenText = "2024 AI광주미래교육박람회"
    )
}
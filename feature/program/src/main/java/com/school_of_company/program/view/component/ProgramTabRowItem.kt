package com.school_of_company.program.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ProgramTabRowItem(
    modifier: Modifier = Modifier,
    isCurrentIndex: Boolean,
    title: String,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .expoClickable(onClick = onClick)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = typography.bodyBold1,
                fontWeight = if (isCurrentIndex) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isCurrentIndex) colors.main else colors.gray500,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ProgramTabRowItemNotCurrentIndexPreview() {
    ProgramTabRowItem(
        isCurrentIndex = false,
        onClick = {},
        title = "제목"
    )
}

@Preview
@Composable
private fun ProgramTabRowItemCurrentIndexPreview() {
    ProgramTabRowItem(
        isCurrentIndex = true,
        onClick = {},
        title = "제목"
    )
}
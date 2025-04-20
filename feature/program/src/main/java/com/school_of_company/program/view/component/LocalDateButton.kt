package com.school_of_company.program.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.time.LocalDate

@Composable
internal fun LocalDateButton(
    modifier: Modifier = Modifier,
    date: LocalDate,
    selected: Boolean,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        val outlineColor = if (selected) colors.main else colors.gray200
        val textColor = if (selected) colors.white else colors.main
        val backGroundColor = if (selected) colors.main else colors.white

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .expoClickable(onClick = onClick)
                .background(
                    color = backGroundColor,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .border(
                    width = 1.dp,
                    color = outlineColor,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Text(
                text = date.toString(),
                style = typography.captionRegular2,
                fontWeight = FontWeight.W400,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
private fun LocalDateButtonPreview() {
    LocalDateButton(
        modifier = Modifier,
        date = LocalDate.now(),
        selected = false,
        onClick = {},
    )
}
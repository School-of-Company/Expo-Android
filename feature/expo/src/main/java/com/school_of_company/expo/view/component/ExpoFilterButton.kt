package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.DownArrowIcon
import com.school_of_company.design_system.icon.FilterIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun HomeFilterButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = colors.main,
                    shape = RoundedCornerShape(8.dp),
                )
                .expoClickable(
                    onClick = onClick,
                    rippleColor = colors.white
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
            ) {
                FilterIcon(
                    tint = colors.main,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = text,
                    style = typography.captionRegular1,
                    color = colors.main,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeFilerButtonPreview() {
    HomeFilterButton(
        text = "오래된 순"
    ) {}
}
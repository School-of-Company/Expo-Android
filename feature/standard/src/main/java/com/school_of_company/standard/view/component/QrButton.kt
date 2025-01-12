package com.school_of_company.standard.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun QrButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(
                    color = colors.main,
                    shape = RoundedCornerShape(10.dp),
                )
                .expoClickable(
                    onClick = onClick,
                    rippleColor = colors.white
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
            ) {
                Text(
                    text = "QR 스캔",
                    style = typography.bodyBold2,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.white,
                )
            }
        }
    }
}

@Preview
@Composable
private fun QrButtonPreview() {
    QrButton(
        onClick = {},
    )
}
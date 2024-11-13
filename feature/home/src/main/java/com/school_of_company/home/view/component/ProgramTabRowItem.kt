package com.school_of_company.home.view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ProgramTabRowItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    isCurrentIndex: Boolean,
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)
                .expoClickable(onClick = onClick)
        ) {
            if (isCurrentIndex) {
                Text(
                    text = title,
                    style = typography.bodyBold1,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.black,
                )
            } else {
                Text(
                    text = title,
                    style = typography.bodyBold1,
                    fontWeight = FontWeight.Normal,
                    color = colors.gray500,
                )
            }
        }
    }
}

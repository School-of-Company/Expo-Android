package com.school_of_company.expo.view.component

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.OutlinedIconTextButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme

@Composable
fun ExpoCreatedDeleteButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
        if (enabled) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .expoClickable { onClick() }
                    .background(
                        color = colors.error,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                TrashIcon(
                    tint = colors.white
                )
                Text(
                    text = "삭제하기",
                    style = typography.titleBold3,
                    fontWeight = FontWeight.W600,
                    color = colors.white,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            OutlinedIconTextButton(
                textValue = "삭제하기",
                leadingIcon = { color -> TrashIcon(tint = color) },
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
fun ExpoCreatedDeleteButtonEnabled() {
    ExpoCreatedDeleteButton(enabled = true) {}
}

@Preview
@Composable
fun ExpoCreatedDeleteButtonDisabled() {
    ExpoCreatedDeleteButton(enabled = false) {}
}
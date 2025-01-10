package com.school_of_company.design_system.component.button

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
fun OutlinedIconTextButton(
    modifier: Modifier = Modifier,
    textValue: String,
    enabled: Boolean = true,
    outlineColor: Color = ExpoColor.gray200,
    contentColor: Color = ExpoColor.gray400,
    leadingIcon: @Composable (Color) -> @Composable Unit,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .expoClickable(enabled = enabled) { onClick() }
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .border(
                    width = 1.dp,
                    color = outlineColor,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            leadingIcon(contentColor)
            Text(
                text = textValue,
                style = typography.titleBold3,
                fontWeight = FontWeight.W600,
                color = contentColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun OutlinedIconTextButtonPreview() {
    OutlinedIconTextButton(
        leadingIcon = { color -> TrashIcon(tint = color) },
        onClick = { },
        textValue = "삭제하기",
    )
}
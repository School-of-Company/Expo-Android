package com.school_of_company.user.view.component

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
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.OutlinedIconTextButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.CheckIcon
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun UserAllowButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        if (enabled) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .expoClickable { onClick() }
                    .background(
                        color = colors.success,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                CheckIcon(tint = colors.white)

                Text(
                    text = "수락",
                    style = typography.titleBold3,
                    fontWeight = FontWeight.W600,
                    color = colors.white,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            OutlinedIconTextButton(
                textValue = "수락",
                leadingIcon = { color -> CheckIcon(tint = color) },
                onClick = onClick
            )
        }
    }
}
package com.school_of_company.design_system.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    onClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        val isEnabled = state == ButtonState.Enable

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = colors.main,
                    shape = RoundedCornerShape(6.dp),
                )
                .expoClickable(
                    onClick = onClick,
                    enabled = isEnabled,
                    rippleColor = colors.white
                )
                .then(modifier)
        ) {
            Text(
                text = text,
                style = typography.bodyBold2,
                fontWeight = FontWeight.SemiBold,
                color = colors.white
            )
        }
    }
}

@Preview
@Composable
private fun ExpoButtonPreview() {
    val isPositiveActionDialogVisible = remember { mutableStateOf(false) }

    Column {
        ExpoButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            text = "뀨뀨뀨뀨뀨뀨",
            state = when (isPositiveActionDialogVisible.value) {
                true -> ButtonState.Enable
                false -> ButtonState.Disable
            }
        ) {}
    }
}
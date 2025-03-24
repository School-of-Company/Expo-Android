package com.school_of_company.design_system.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    color: Color,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(6.dp),
                )
                .expoClickable(
                    onClick = onClick,
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

@Composable
fun ExpoStateButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() }

        val enabledState: (buttonState: ButtonState) -> Boolean = {
            when (it) {
                ButtonState.Enable -> true
                ButtonState.Disable -> false
            }
        }
        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            enabled = enabledState(state),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main,
                contentColor = colors.white,
                disabledContainerColor = colors.gray200,
                disabledContentColor = colors.gray400
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = onClick,
        ) {
            Text(
                text = text,
                style = typography.bodyBold2
            )
        }
    }
}

@Composable
fun ExpoEnableButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = colors.white,
            ),
            shape = RoundedCornerShape(6.dp),
            onClick = onClick,
        ) {
            Text(
                text = text,
                style = typography.bodyBold2,
                color = textColor
            )
        }
    }
}

@Composable
fun ExpoEnableDetailButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        val interactionSource = remember { MutableInteractionSource() }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main100,
                contentColor = colors.main,
            ),
            shape = RoundedCornerShape(6.dp),
            onClick = onClick,
        ) {
            Text(
                text = text,
                style = typography.bodyBold2,
                color = colors.main
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
            color = Color.Blue,
            onClick = { isPositiveActionDialogVisible.value = true }
        )
    }
}

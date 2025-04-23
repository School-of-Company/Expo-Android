package com.school_of_company.signup.view.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.coroutines.delay

@Composable
internal fun TimeExpoStateButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        var isEnabled by remember { mutableStateOf(true) }
        var remainingAttempts by remember { mutableStateOf(5) }
        var timerStarted by remember { mutableStateOf(true) }
        var showCountdown by remember { mutableStateOf(true) }

        LaunchedEffect(timerStarted) {
            if (timerStarted) {
                isEnabled = false
                delay(300_000L) // 5분 대기
                isEnabled = true
                timerStarted = false
                showCountdown = false
            }
        }

        val enabledState: (buttonState: ButtonState) -> Boolean = {
            when (it) {
                ButtonState.Enable -> true
                ButtonState.Disable -> false
            }
        }

        val interactionSource = remember { MutableInteractionSource() }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            enabled = if (isEnabled && remainingAttempts > 0) enabledState(ButtonState.Enable) else enabledState(ButtonState.Disable),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main,
                contentColor = colors.white,
                disabledContainerColor = colors.gray200,
                disabledContentColor = colors.gray400
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                if (isEnabled && remainingAttempts > 0) {
                    isEnabled = false
                    remainingAttempts--
                    timerStarted = true
                    showCountdown = true
                }
                onClick()
            }
        ) {
            if (showCountdown) {
                CountdownTimer(
                    onTimerFinish = { showCountdown = false }
                )
            } else {
                Text(
                    text = text,
                    style = typography.bodyBold2
                )
            }
        }
    }
}
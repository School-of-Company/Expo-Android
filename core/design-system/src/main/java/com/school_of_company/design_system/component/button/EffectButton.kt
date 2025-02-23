package com.school_of_company.design_system.component.button

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.coroutines.delay

@Composable
fun EffectButton(
    modifier: Modifier = Modifier,
    text: String,
    defaultTextColor: Color = Color.Black,
    clickedTextColor: Color = Color.White,
    defaultBackgroundColor: Color = Color.White,
    clickedBackgroundColor: Color = Color.Red,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { _, typography ->

        val interactionSource = remember { MutableInteractionSource() }
        var isClicked by remember { mutableStateOf(false) }

        LaunchedEffect(isClicked) {
            delay(100)
            isClicked = false
        }

        Button(
            modifier = modifier,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isClicked) clickedBackgroundColor else defaultBackgroundColor,
                contentColor = if (isClicked) clickedTextColor else defaultTextColor,
            ),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                isClicked = true
                onClick()
            },
        ) {
            Text(
                text = text,
                style = typography.bodyBold2,
                color = if (isClicked) clickedTextColor else defaultTextColor
            )
        }
    }
}

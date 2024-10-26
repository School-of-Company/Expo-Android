package com.school_of_company.design_system.component.textfield

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ErrorText(
    text: String,
) {
    ExpoAndroidTheme { colors, typography ->
        Text(
            text = text,
            color = colors.error,
            style = typography.captionRegular2,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ExpoDefaultTextField(
    modifier: Modifier,
    label: String,
    placeholder: String,
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusManager: FocusManager = LocalFocusManager.current,
    isError: Boolean,
    isDisabled: Boolean,
    errorText: String,
    onValueChange: (String) -> Unit,
    onClicked: (() -> Unit)? = null,
    value: String? = null,
    visualTransformationState: Boolean = false,
) {
    var text by remember { mutableStateOf(value ?: "") }
    val isFocused = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }

    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = label,
                color = colors.black,
                style = typography.bodyBold2,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = value ?: it
                    onValueChange(it)
                },
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = when {
                            isDisabled -> colors.gray100
                            isError -> colors.error
                            isFocused.value -> colors.main
                            text.isNotEmpty() -> colors.gray100
                            else -> colors.gray100
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .onFocusChanged {
                        isFocused.value = it.isFocused
                        if (it.isFocused && onClicked != null) onClicked()
                        if (!it.isFocused && value != null) text = value
                    }
                    .background(
                        color = if (isDisabled) colors.white else Color.Transparent
                    ),
                textStyle = typography.captionRegular2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedPlaceholderColor = colors.gray300,
                    unfocusedPlaceholderColor = colors.gray300,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = if (isError) colors.error else colors.black,
                    unfocusedTextColor = if (isError) colors.error else colors.black,
                    disabledTextColor = colors.gray300,
                    cursorColor = colors.main
                ),
                enabled = !isDisabled,
                placeholder = {
                    Text(text = placeholder, style = typography.captionRegular2)
                },
                maxLines = 1,
                singleLine = true,
                readOnly = isReadOnly,
                keyboardOptions = keyboardOptions,
                visualTransformation = if (visualTransformationState) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (isError) {
                Row(horizontalArrangement = if (isError) Arrangement.Start else Arrangement.End) {
                    ErrorText(text = errorText)
                }
            }
        }
    }
}


@Preview
@Composable
fun ExpoOutlinedTextFieldPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)) {
            ExpoDefaultTextField(
                modifier = Modifier
                    .width(320.dp)
                    .height(52.dp),
                placeholder = "Put Email",
                errorText = "Incorrect Format",
                onValueChange = {},
                isError = true,
                isDisabled = false,
                isReadOnly = false,
                onClicked = {},
                label = "이메일",
            )
            ExpoDefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = "Put Email",
                errorText = "Incorrect Format",
                onValueChange = {},
                isError = false,
                isDisabled = false,
                isReadOnly = false,
                onClicked = {},
                label = "비밀번호"
            )
        }
    }
}
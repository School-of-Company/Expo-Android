package com.school_of_company.design_system.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoOutlineTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean = false,
    placeholder: String = "",
    errorPlaceholder: String = "",
    readOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
    maxLength: Int = Int.MAX_VALUE,
) {
    val isFocused = remember { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->

        DisposableEffect(Unit) {
            onDispose {
                focusManager.clearFocus()
            }
        }

        Column{
            Text(
                text = label,
                style = typography.bodyBold2,
                fontWeight = FontWeight.Bold,
                color = colors.black
            )

            Spacer(modifier = modifier.padding(bottom = 10.dp))

            OutlinedTextField(
                value = value,
                onValueChange = {
                    val filteredText = it.filterNot { text -> text.isWhitespace() }
                    if (filteredText.length <= maxLength) {
                        onValueChange(filteredText)
                    }
                },
                placeholder = {
                    if (isError) {
                        ErrorText(text = errorPlaceholder)
                    } else {
                        Text(
                            text = placeholder,
                            style = typography.captionRegular1,
                            fontWeight = FontWeight.Normal,
                            color = colors.gray200
                        )
                    }
                },
                maxLines = maxLines,
                singleLine = singleLine,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                readOnly = readOnly,
                visualTransformation = visualTransformation,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = if (isError) colors.error else colors.black,
                    unfocusedTextColor = if (isError) colors.error else colors.black,
                    focusedPlaceholderColor = if (isError) colors.error else colors.gray200,
                    unfocusedPlaceholderColor = if (isError) colors.error else colors.gray200,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = colors.gray400
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .focusRequester(focusRequester)
                    .clip(RoundedCornerShape(6.dp))
                    .border(
                        width = 1.dp,
                        color = if (isError) colors.error else colors.gray100,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .background(colors.white)
                    .onFocusChanged { isFocused.value = it.isFocused }
            )
        }
    }
}

@Composable
fun ErrorText(
    text: String,
) {
    ExpoAndroidTheme { colors, typography ->
        Text(
            text = text,
            color = colors.error,
            style = typography.captionRegular1,
            fontWeight = FontWeight.Normal
        )
    }
}


@Preview
@Composable
fun ExpoOutlinedTextField() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),) {
            ExpoOutlineTextField(
                label = "아이디",
                value = "",
                onValueChange = {},
                placeholder = "아이디를 입력해주세요",
            )
            ExpoOutlineTextField(
                label = "로그인",
                value = "",
                onValueChange = {},
                placeholder = "아이디를 입력해주세요.",
                errorPlaceholder = "아이디를 잘못입력했습니다.",
                isError = true
            )
        }
    }
}
package com.school_of_company.design_system.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.LocationIcon
import com.school_of_company.design_system.icon.SearchIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    text: String,
) {
    ExpoAndroidTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = text,
            color = colors.error,
            style = typography.captionRegular2,
            fontWeight = FontWeight.Normal
        )
    }
}

// TODO: 법용성 증가, textField 통합

@Composable
fun ExpoDefaultTextField(
    modifier: Modifier,
    value: String? = null,
    label: String,
    placeholder: String,
    errorText: String,
    isError: Boolean,
    isDisabled: Boolean,
    isReadOnly: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformationState: Boolean = false,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
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
                    text = it
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
                    .onFocusChanged { isFocused.value = it.isFocused }
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
                visualTransformation = if (visualTransformationState) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = trailingIcon
            )
            if (isError) {// TODO: 항상 참인 식
                Row(horizontalArrangement = if (isError) Arrangement.Start else Arrangement.End) {
                    ErrorText(text = errorText)
                }
            }
        }
    }
}

@Composable
fun ExpoNoneLabelTextField(
    modifier: Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorText: String,
    isError: Boolean,
    isDisabled: Boolean,
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformationState: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val text by remember(value) { derivedStateOf { value ?: "" } }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ExpoAndroidTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                modifier = modifier
                    .focusRequester(focusRequester)
                    .border(
                        width = 1.dp,
                        color = when {
                            isDisabled -> colors.gray100
                            isError -> colors.error
                            isFocused -> colors.main
                            text.isNotEmpty() -> colors.gray100
                            else -> colors.gray100
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .onFocusChanged { isFocused = it.isFocused }
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
                visualTransformation = if (visualTransformationState) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = trailingIcon
            )
            if (isError) { // TODO: 항상 참인 식 
                Row(horizontalArrangement = if (isError) Arrangement.Start else Arrangement.End) {
                    ErrorText(text = errorText)
                }
            }
        }
    }
}

@Composable
fun LimitedLengthTextField(
    modifier: Modifier = Modifier,
    updateTextValue: (String) -> Unit,
    value: String,
    placeholder: String,
    overflowErrorMessage: String = "",
    label: String = "",
    isError: Boolean,
    showLengthCounter: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    lengthLimit: Int = 0,
) {
    val lengthCheck = remember {
        if (lengthLimit != 0) value.length >= lengthLimit else false
    }

    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier.background(color = Color.Transparent)
        ) {
            Text(
                text = label,
                style = typography.bodyBold2,
                color = colors.black,
                fontWeight = FontWeight(600),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (lengthCheck || isError) colors.error else colors.gray100,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(16.dp)
            ) {
                BasicTextField(
                    onValueChange = { newText ->
                        if (lengthLimit == 0 || newText.length <= lengthLimit) {
                            updateTextValue(newText)
                        }
                    },
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    value = value,
                    textStyle = typography.captionRegular1.copy(
                        fontWeight = FontWeight.Normal,
                        color = colors.black,
                        textAlign = TextAlign.Start,
                    ),
                    cursorBrush = SolidColor(colors.main),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 10.dp, max = 300.dp)
                )

                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = typography.captionRegular1,
                        fontWeight = FontWeight.Normal,
                        color = colors.gray300,
                    )
                }
            }

            if (lengthLimit != 0 && showLengthCounter) {
                Text(
                    text = "${value.length} / $lengthLimit",
                    style = typography.captionRegular2,
                    fontWeight = FontWeight.Normal,
                    color = colors.main,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            if (lengthCheck) {
                Text(
                    text = overflowErrorMessage,
                    style = typography.captionRegular2,
                    fontWeight = FontWeight.Normal,
                    color = colors.error
                )
            }
        }
    }
}


@Composable
fun NoneLimitedLengthTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    updateTextValue: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier.background(color = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colors.gray100,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(16.dp)
            ) {
                BasicTextField(
                    onValueChange = { newText ->
                        updateTextValue(newText)
                    },
                    keyboardOptions = keyboardOptions,
                    value = value,
                    textStyle = typography.captionRegular1.copy(
                        fontWeight = FontWeight.Normal,
                        color = colors.black,
                        textAlign = TextAlign.Start,
                    ),
                    cursorBrush = SolidColor(colors.main),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 10.dp, max = 300.dp)
                )

                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = typography.captionRegular1,
                        fontWeight = FontWeight.Normal,
                        color = colors.gray300,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpoLocationIconTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isDisabled: Boolean,
    onButtonClicked: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Box {
            OutlinedTextField(
                placeholder = {
                    Text(
                        text = placeholder,
                        style = typography.captionRegular1,
                        color = colors.gray300
                    )
                },
                value = value,
                onValueChange = { newState ->
                    onValueChange(newState)
                },
                modifier = modifier
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(6.dp),
                        color = colors.gray100
                    )
                    .background(color = Color.Transparent)
                    .fillMaxWidth(),
                textStyle = typography.captionRegular1.copy(color = colors.black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colors.black,
                    unfocusedTextColor = colors.black,
                    cursorColor = colors.main,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                enabled = !isDisabled,
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = onButtonClicked) {
                        LocationIcon(
                            tint = colors.gray500,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    textStyle: TextStyle,
    updateTextValue: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    ExpoAndroidTheme { colors, _ ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                BasicTextField(
                    onValueChange = { newText ->
                        updateTextValue(newText)
                    },
                    keyboardOptions = keyboardOptions,
                    value = value,
                    textStyle = textStyle,
                    cursorBrush = SolidColor(colors.main),
                    modifier = Modifier.heightIn(min = 10.dp, max = 300.dp)
                )

                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = textStyle,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpoSearchIconTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isDisabled: Boolean,
    onButtonClicked: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Box {
            OutlinedTextField(
                placeholder = {
                    Text(
                        text = placeholder,
                        style = typography.captionRegular1,
                        color = colors.gray300
                    )
                },
                value = value,
                onValueChange = { newState ->
                    onValueChange(newState)
                },
                modifier = modifier
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(6.dp),
                        color = colors.gray100
                    )
                    .background(color = Color.Transparent)
                    .fillMaxWidth(),
                textStyle = typography.captionRegular1.copy(color = colors.black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colors.black,
                    unfocusedTextColor = colors.black,
                    cursorColor = colors.main,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                enabled = !isDisabled,
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = onButtonClicked) {
                        SearchIcon(
                            tint = colors.gray500,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
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
                label = "비밀번호"
            )
            LimitedLengthTextField(
                value = "",
                placeholder = "",
                isError = false,
                updateTextValue = {})

            NoneLimitedLengthTextField(value = "", placeholder = "", updateTextValue = {})

            TransparentTextField(value = "배경없는 textField", placeholder = "", updateTextValue = {}, textStyle = ExpoTypography.bodyBold2.copy(fontWeight = FontWeight.W600))
        }
    }
}
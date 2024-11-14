package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.LocationIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun HomeDetailModifyIconTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    isDisabled: Boolean,
    onValueChange: (String) -> Unit,
    onButtonClicked: () -> Unit,
    value: String
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

@Preview
@Composable
private fun HomeDetailModifyIconTextFieldPreview() {
    HomeDetailModifyIconTextField(
        modifier = Modifier,
        placeholder = "연수 종류를 입력해주세요.",
        isDisabled = false,
        onValueChange = {},
        onButtonClicked = {},
        value = ""
    )
}
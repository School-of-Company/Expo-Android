package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
internal fun ExpoNoneLineTextField(
    modifier: Modifier = Modifier,
    textState: String,
    placeHolder: @Composable () -> Unit,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onTextChange: (String) -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier.fillMaxWidth()
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                value = textState,
                onValueChange = { newText -> onTextChange(newText) },
                cursorBrush = SolidColor(colors.main),
                singleLine = singleLine,
                minLines = minLines,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                textStyle = typography.titleBold2,
                decorationBox = { innerTextField ->
                    Column(verticalArrangement = Arrangement.Center) {
                        if (textState.isEmpty()) {
                            placeHolder()
                        }
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview
@Composable
private fun ExpoNoneLineTextFieldPreview() {
    ExpoNoneLineTextField(
        textState = "",
        placeHolder = {
            Text(
                text = "yyyy-MM-dd HH:mm",
                style = ExpoTypography.titleBold2,
                color = ExpoColor.gray300
            )
        },
        onTextChange = {}
    )
}
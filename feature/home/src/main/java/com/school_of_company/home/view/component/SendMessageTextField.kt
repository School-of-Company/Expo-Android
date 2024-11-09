package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.R

@Composable
fun SendMessageTextField(
    modifier: Modifier = Modifier,
    label: String,
    textState: String,
    placeholder: String,
    overflowErrorMessage: String = "",
    isError: Boolean,
    lengthLimit: Int = 0,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    updateTextValue: (String) -> Unit,
) {
    val lengthCheck = remember {
        if (lengthLimit != 0) textState.length >= lengthLimit else false
    }

    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier.background(color = Color.Transparent)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = label,
                        style = typography.bodyBold2,
                        color = colors.black,
                        fontWeight = FontWeight(600),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
                LazyColumn {
                    item {
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
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                BasicTextField(
                                    onValueChange = { newText ->
                                        if (lengthLimit != 0) {
                                            if (newText.length <= lengthLimit) {
                                                updateTextValue(newText)
                                            }
                                        } else {
                                            updateTextValue(newText)
                                        }
                                    },
                                    keyboardOptions = keyboardOptions,
                                    value = textState,
                                    textStyle = typography.captionRegular1.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = colors.black,
                                        textAlign = TextAlign.Start,
                                    ),
                                    cursorBrush = SolidColor(colors.main),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                if (textState.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        style = typography.captionRegular1,
                                        fontWeight = FontWeight.Normal,
                                        color = colors.gray300,
                                    )
                                }
                            }
                        }
                    }
                }
                if (lengthLimit != 0) {
                    Row(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.End),
                    ) {
                        Text(
                            text = textState.length.toString(),
                            style = typography.captionRegular2,
                            fontWeight = FontWeight.Normal,
                            color = colors.main,
                        )
                        Text(
                            text = stringResource(R.string.slash) + lengthLimit,
                            style = typography.captionRegular2,
                            fontWeight = FontWeight.Normal,
                            color = colors.main,
                        )
                    }
                }
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

@Preview
@Composable
private fun SendMessageTextFieldPreview() {
    SendMessageTextField(
        label = "제목",
        textState = "asdfasdf",
        placeholder = "제목을 입력해주세요",
        isError = false,
        lengthLimit = 300,
    ) {

    }
}
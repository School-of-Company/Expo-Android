package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.PlusIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoAddTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    trainingTextFieldList: List<String>,
    onValueChange: (Int, String) -> Unit,
    onAddTextField: () -> Unit,
    onRemoveTextField: (Int) -> Unit,
    onTrainingSetting: () -> Unit
) {

    ExpoAndroidTheme { colors, typography ->

        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(horizontal = 16.dp, vertical = 26.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                trainingTextFieldList.forEachIndexed { index, text ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = typography.bodyRegular2,
                            color = colors.gray500,
                        )

                        Spacer(modifier = Modifier.padding(end = 12.dp))

                        BasicTextField(
                            value = text,
                            onValueChange = { newState ->
                                onValueChange(index, newState)
                            },
                            textStyle = typography.bodyRegular2,
                            cursorBrush = SolidColor(colors.main),
                            decorationBox = { innerTextField ->
                                if (text.isEmpty()) {
                                    Text(
                                        text = placeHolder,
                                        style = typography.bodyRegular2,
                                        color = colors.gray300
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )

                        TrainingSettingButton(
                            text = "연수설정",
                            onClick = onTrainingSetting
                        )

                        IconButton(onClick = { onRemoveTextField(index) }) {
                            XIcon(tint = colors.black)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .expoClickable { onAddTextField() }
                        .align(Alignment.CenterHorizontally)
                ) {
                    PlusIcon(tint = colors.main300)

                    Text(
                        text = "추가하기",
                        style = typography.captionBold1,
                        color = colors.main300
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExpoAddTextFieldPreview() {
    ExpoAddTextField(
        placeHolder = "연수를 입력하세요.",
        trainingTextFieldList = listOf(""),
        onValueChange = { _, _ ->},
        onAddTextField = { /*TODO*/ },
        onRemoveTextField = { /*TODO*/ },
        onTrainingSetting = { /*TODO*/ }

    )
}
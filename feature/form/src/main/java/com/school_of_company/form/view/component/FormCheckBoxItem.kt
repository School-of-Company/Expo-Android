package com.school_of_company.form.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.TransparentTextField
import com.school_of_company.design_system.icon.CircleIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun FormCheckBoxItem(
    modifier: Modifier = Modifier,
    isEtc: Boolean = false,
    itemIndex: Int = 0,
    description: String = "",
    onXClick: (Int) -> Unit,
    updateTextValue: (String) -> Unit = { },
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            modifier = modifier
                .padding(vertical = 4.dp)
                .background(colors.white),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircleIcon(
                    modifier = Modifier.size(16.dp),
                    tint = colors.gray500,
                )

                if (isEtc) {
                    Text(
                        text = "기타",
                        style = typography.captionRegular1,
                        fontWeight = FontWeight.W400,
                        color = colors.black,
                    )

                    Text(
                        text = "(직접입력)",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.gray300
                    )
                } else {
                    TransparentTextField(
                        placeholder = "문장을 입력하세요",
                        value = description,
                        textStyle = typography.captionRegular1,
                        updateTextValue = updateTextValue
                    )
                }
            }

            XIcon(
                modifier = Modifier.expoClickable { onXClick(itemIndex) },
                tint = colors.gray500,
            )
        }
    }
}
@Preview
@Composable
private fun FormCheckBoxItemPreview() {
    Column {
        FormCheckBoxItem(
            modifier = Modifier.fillMaxWidth(),
            description = "여긴 어디",
            isEtc = false,
            itemIndex = 0,
            onXClick = { },
            updateTextValue = { _ -> }
        )
        FormCheckBoxItem(
            modifier = Modifier.fillMaxWidth(),
            description = "여긴 어디",
            isEtc = true,
            itemIndex = 0,
            onXClick = { },
            updateTextValue = { _ -> }
        )
        FormCheckBoxItem(
            modifier = Modifier.fillMaxWidth(),
            description = "",
            isEtc = false,
            itemIndex = 0,
            onXClick = { },
            updateTextValue = { _ -> }
        )
    }
}
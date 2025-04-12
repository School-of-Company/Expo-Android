package com.school_of_company.form.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.textfield.TransparentTextField
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun PersonaInformationFormCard(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(size = 6.dp),
                )
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(size = 6.dp),
                )
                .padding(16.dp),
        ) {
            Text(
                text = "개인정보 동의 안내 문장을 입력해주세요.",
                style = typography.titleBold3,
                fontWeight = FontWeight.W600,
                color = colors.black,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.gray100),
            )

            TransparentTextField(
                placeholder = "문장을 입력해주세요",
                value = value,
                textStyle = typography.captionBold2,
                placeholderTextStyle = typography.captionBold2.copy(color = colors.gray500),
                updateTextValue = onTextChange
            )
        }
    }
}

@Preview
@Composable
private fun PersonaInformationFormCardPreview() {
    var value by remember { mutableStateOf("") }

    PersonaInformationFormCard(
        modifier = Modifier,
        value = value,
        onTextChange = { newValue -> value = newValue },
    )
}
package com.school_of_company.expo.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun TrainingSettingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(all = 8.dp)
                .expoClickable(onClick = onClick)
        ) {
            Text(
                text = text,
                style = typography.captionRegular2,
                color = colors.gray300,
            )
        }
    }
}

@Preview
@Composable
private fun TrainingSettingButtonPreview() {
    TrainingSettingButton(text = "수정하기") {}
}
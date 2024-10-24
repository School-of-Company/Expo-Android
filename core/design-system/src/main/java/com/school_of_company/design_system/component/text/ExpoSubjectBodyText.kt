package com.school_of_company.design_system.component.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoSubjectTitleText(subjectText: String, ) {
    ExpoAndroidTheme { colors, typography ->
        Column {
            Text(
                text = subjectText,
                color = colors.black,
                style = typography.bodyBold1,
            )
        }
    }
}
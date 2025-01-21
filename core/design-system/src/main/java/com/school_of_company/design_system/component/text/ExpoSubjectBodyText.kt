package com.school_of_company.design_system.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ExpoSubjectTitleText(
    modifier: Modifier = Modifier,
    subjectText: String,
) {
    ExpoAndroidTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = subjectText,
            color = colors.black,
            style = typography.bodyBold1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
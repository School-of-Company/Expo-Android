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
import com.school_of_company.design_system.icon.ImageIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun FormSentenceItem(
    modifier: Modifier = Modifier,
    onXClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            modifier = modifier
                .padding(vertical = 4.dp)
                .background(colors.white),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "업로드",
                style = typography.captionRegular1,
                fontWeight = FontWeight.W400,
                color = colors.black,
            )

            XIcon(
                modifier = Modifier.expoClickable(onClick = onXClick),
                tint = colors.gray500,
            )
        }
    }
}

@Preview
@Composable
private fun FormSentenceItemPreview() {
    Column {
        FormSentenceItem(
            modifier = Modifier.fillMaxWidth(),
            onXClick = { },
        )
    }
}
package com.school_of_company.sms.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.CheckIcon
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun HomeSendMessageTopBar(
    modifier: Modifier = Modifier,
    startIcon: @Composable () -> Unit,
    betweenText: String = "",
    endIcon: @Composable () -> Unit = { Spacer(modifier = Modifier.size(24.dp)) }
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            startIcon()
            Text(
                text = betweenText,
                style = typography.bodyRegular1,
                fontWeight = FontWeight(400),
                color = colors.black
            )
            endIcon()
        }
    }
}

@Preview
@Composable
private fun HomeSendMessageTopBarPreview() {
    HomeSendMessageTopBar(
        startIcon = { TrashIcon() },
        betweenText = "사이 텍스트 입니다",
        endIcon = { CheckIcon() },
    )
}
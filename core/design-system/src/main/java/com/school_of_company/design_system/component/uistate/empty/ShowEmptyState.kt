package com.school_of_company.design_system.component.uistate.empty

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.ExpoIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun ShowEmptyState(
    modifier: Modifier = Modifier,
    emptyMessage: String,
    scrollState: ScrollState,
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                28.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = colors.white)
        ) {
            ExpoIcon(
                tint = colors.black,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = emptyMessage,
                style = typography.bodyRegular2,
                color = colors.gray400
            )
        }
    }
}
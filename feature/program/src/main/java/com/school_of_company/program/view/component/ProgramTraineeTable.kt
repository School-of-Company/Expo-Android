package com.school_of_company.program.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ProgramTraineeTable(
    modifier: Modifier = Modifier,
    scrollState: ScrollState
) {
    ExpoAndroidTheme { colors, typography ->

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = colors.gray200)
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(44.dp)
            ) {
                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "성명",
                    style = typography.captionBold1,
                    color = colors.gray600,
                    modifier = Modifier.requiredWidthIn(80.dp)
                )

                Text(
                    text = "연수원 아이디",
                    style = typography.captionBold1,
                    color = colors.gray600,
                    modifier = Modifier.requiredWidthIn(130.dp)
                )

                Text(
                    text = "휴대폰 번호",
                    style = typography.captionBold1,
                    color = colors.gray600,
                    modifier = Modifier.requiredWidthIn(130.dp)
                )

                Text(
                    text = "현장 및 사전",
                    style = typography.captionBold1,
                    color = colors.gray600,
                    modifier = Modifier.requiredWidthIn(100.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = colors.gray100)
        )
    }
}
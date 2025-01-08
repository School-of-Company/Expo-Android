package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme

@Composable
fun ExpoCreatedTopCard(
    modifier: Modifier = Modifier,
    participantCount: Int,
) {
    ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
        ) {
            Text(
                text = "등록된 박람회",
                style = typography.bodyBold2,
                fontWeight = FontWeight.W600,
                color = colors.black,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WarnIcon(tint = colors.gray300)
                    Text(
                        text = "옆으로 넘겨서 확인해보세요",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.gray300,
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(14.dp)
                        .background(color = colors.gray100)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "참가자 전체 인원",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.gray500,
                    )
                    Text(
                        text = "${participantCount}명",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.main,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpoCreatedTopCardPreview(){
    ExpoCreatedTopCard(
        participantCount = 30
    )
}
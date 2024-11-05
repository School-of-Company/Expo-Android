package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun MessageDialog(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onParticipantClick: () -> Unit,
    onTraineeClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(
                    colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .paddingHorizontal(horizontal = 12.dp, top = 18.dp, bottom = 18.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "누구에게 문자를 전송하시겠습니까?",
                    style = typography.bodyBold2,
                    fontWeight = FontWeight(600)
                )

                XIcon(modifier = Modifier.expoClickable { onCancelClick() })
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),) {

                ExpoButton(
                    text = "참가자",
                    color = colors.main500,
                    onClick = onParticipantClick,
                    modifier = Modifier
                        .width(124.dp)
                        .padding(vertical = 14.dp)
                )

                ExpoButton(
                    text = "연수자",
                    color = colors.main300,
                    onClick = onTraineeClick,
                    modifier = Modifier
                        .width(124.dp)
                        .padding(vertical = 14.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MessageDialogPreview() {
    MessageDialog(
        onCancelClick = {},
        onParticipantClick = {},
        onTraineeClick = {}
    )
}
package com.school_of_company.user.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.ExpoEnableButton
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun UserDialog(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    titleText: String,
    contentText: String,
    buttonText: String
) {

    ExpoAndroidTheme { colors, typography ->

        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(
                    colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(all = 26.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {

                Text(
                    text = titleText,
                    color = colors.black,
                    style = typography.titleBold3,
                )

                Text(
                    text = contentText,
                    color = colors.gray500,
                    style = typography.bodyRegular2
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {

                ExpoEnableButton(
                    text = buttonText,
                    onClick = onConfirmClick,
                    textColor = colors.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colors.error,
                            shape = RoundedCornerShape(6.dp)
                        )
                )

                ExpoEnableButton(
                    text = "취소",
                    onClick = onCancelClick,
                    textColor = colors.gray700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colors.gray700,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserDialogPreview() {
    UserDialog(
        onCancelClick = { /*TODO*/ },
        onConfirmClick = { /*TODO*/ },
        titleText = "정말로 로그아웃 하시겠습니까?",
        contentText = "로그아웃 했을 경우 다시 로그인 해야하는 경우가 발생합니다",
        buttonText = "로그아웃"
    )
}
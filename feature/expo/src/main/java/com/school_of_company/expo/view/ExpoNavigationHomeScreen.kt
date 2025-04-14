package com.school_of_company.expo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.lottie.CheckLottie
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.RightArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ExpoNavigationHomeRoute(
    navigateToHome: () -> Unit
) {

    ExpoNavigationHomeScreen(
        navigateToHome = navigateToHome
    )
}


@Composable
private fun ExpoNavigationHomeScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {

            CheckLottie()

            Text(
                text = "폼 생성이 완료되었습니다",
                style = typography.titleRegular3,
                color = colors.main
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.expoClickable { navigateToHome() }

            ) {
                Text(
                    text = "홈으로 가기",
                    style = typography.bodyRegular2,
                    color = colors.gray400
                )

                RightArrowIcon(
                    tint = colors.gray400,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewExpoNavigationHomeScreen() {
    ExpoNavigationHomeRoute(
        navigateToHome = { }
    )
}
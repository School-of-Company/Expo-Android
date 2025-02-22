package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.EffectButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ExpoDetailModifyDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    startButtonText: String,
    endButtonText: String,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->

        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top),
            modifier = modifier
                .background(
                    colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .paddingHorizontal(
                    horizontal = 18.dp,
                    top = 18.dp,
                    bottom = 18.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = titleText,
                    color = colors.black,
                    style = typography.bodyBold2,
                    fontWeight = FontWeight(600),
                )

                Spacer(modifier = Modifier.weight(1f))

                XIcon(
                    tint = colors.black,
                    modifier = Modifier.expoClickable { onDismissClick() }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)) {

                EffectButton(
                    text = startButtonText,
                    onClick = onStartClick,
                    defaultTextColor = colors.main,
                    defaultBackgroundColor = colors.white,
                    clickedTextColor = colors.white,
                    clickedBackgroundColor = colors.main,
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main,
                            shape = RoundedCornerShape(6.dp)
                        )
                )

                EffectButton(
                    text = endButtonText,
                    onClick = onEndClick,
                    defaultTextColor = colors.main,
                    defaultBackgroundColor = colors.white,
                    clickedTextColor = colors.white,
                    clickedBackgroundColor = colors.main,
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = colors.main,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpoDetailModifyDialogPreview() {
    ExpoDetailModifyDialog(
        titleText = "수정할 항목을 선택하세요",
        startButtonText = "박람회",
        endButtonText = "폼",
        onStartClick = { /*TODO*/ },
        onEndClick = { /*TODO*/ },
        onDismissClick = {}
    )
}
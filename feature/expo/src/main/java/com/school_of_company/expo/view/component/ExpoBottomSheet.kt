package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    onRecentClick: () -> Unit,
    onOldClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ExpoAndroidTheme { colors, _ ->

        ModalBottomSheet(
            onDismissRequest = { onCancelClick() },
            sheetState = sheetState,
            containerColor = colors.white,
            shape = RoundedCornerShape(
                topStart = 6.dp,
                topEnd = 6.dp
            )
        ) {
            Column(
                modifier = modifier
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 6.dp
                        )
                    )
            ) {
                HomeBottomSheetOptions(
                    text = stringResource(id = R.string.array_recent),
                    onClick = onRecentClick,
                    textColor = colors.black,
                    rippleColor = colors.main
                )

                HomeBottomSheetOptions(
                    text = stringResource(id = R.string.array_older),
                    onClick = onOldClick,
                    textColor = colors.black,
                    rippleColor = colors.main
                )

                HomeBottomSheetOptions(
                    text = stringResource(id = R.string.cancel),
                    onClick = onCancelClick,
                    textColor = colors.error,
                    rippleColor = colors.error
                )
            }
        }
    }
}

@Composable
private fun HomeBottomSheetOptions(
    text: String,
    onClick: () -> Unit,
    textColor: Color,
    rippleColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = ripple(color = rippleColor),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = text,
            style = ExpoTypography.bodyRegular2,
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 14.dp)
        )
    }
}

@Preview
@Composable
private fun HomeBottomSheetPreview() {
    HomeBottomSheet(
        onRecentClick = {},
        onOldClick = {},
        onCancelClick = {}
    )
}
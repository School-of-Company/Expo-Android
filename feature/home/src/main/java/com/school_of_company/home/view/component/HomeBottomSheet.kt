package com.school_of_company.home.view.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    onRecentClick: () -> Unit,
    onOldClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ExpoAndroidTheme { colors, typography ->

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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onRecentClick,
                            indication = ripple(color = colors.main),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.array_recent),
                        style = typography.bodyRegular2,
                        color = colors.black,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 14.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onOldClick,
                            indication = ripple(color = colors.main),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.array_older),
                        style = typography.bodyRegular2,
                        color = colors.black,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 14.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onCancelClick,
                            indication = ripple(color = colors.error),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = typography.bodyRegular2,
                        color = colors.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 14.dp)
                    )
                }
            }
        }
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
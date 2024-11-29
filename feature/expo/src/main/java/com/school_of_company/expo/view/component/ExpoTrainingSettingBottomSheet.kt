package com.school_of_company.expo.view.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.CheckIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.color.ExpoColor
import com.school_of_company.expo.enum.TrainingCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpoTrainingSettingBottomSheet(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    startedTextState: String,
    endedTextState: String,
    onStartedTextChange: (String) -> Unit,
    onEndedTextChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    categoryState: TrainingCategory = TrainingCategory.CHOICE,
    onCategoryChange: (TrainingCategory) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current
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
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 6.dp
                        )
                    )
                    .padding(
                        horizontal = 18.dp,
                        vertical = 24.dp
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                            }
                        )
                    }
                    .imePadding()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "연수 설정",
                        style = typography.titleBold3,
                        color = colors.black,
                        modifier = Modifier.weight(1f)
                    )

                    XIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onCancelClick() }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                        modifier = Modifier.weight(1f)
                    ) {
                        ExpoNoneLineTextField(
                            textState = startedTextState,
                            placeHolder = {
                                Text(
                                    text = "yyyy-MM-dd HH:mm",
                                    style = typography.titleBold2,
                                    color = colors.gray300,
                                    maxLines = 1
                                )
                            },
                            onTextChange = { newText ->
                                onStartedTextChange(newText)
                            }
                        )

                        ExpoNoneLineTextField(
                            textState = endedTextState,
                            placeHolder = {
                                Text(
                                    text = "yyyy-MM-dd HH:mm",
                                    style = typography.titleBold2,
                                    color = colors.gray300,
                                    maxLines = 1
                                )
                            },
                            onTextChange = { newText ->
                                onEndedTextChange(newText)
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "필수",
                            style = typography.bodyRegular2,
                            color = colors.gray500
                        )

                        CustomCheckBox(
                            categoryState = categoryState,
                            onCategoryChange = onCategoryChange
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(top = 10.dp))

                ExpoStateButton(
                    text = "확인",
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    primaryColor: ExpoColor = ExpoColor,
    categoryState: TrainingCategory = TrainingCategory.CHOICE,
    onCategoryChange: (TrainingCategory) -> Unit
) {
    var category by remember { mutableStateOf(categoryState) }

    val animatedColor by animateColorAsState(
        if (category == TrainingCategory.ESSENTIAL) ExpoColor.white else primaryColor.white,
        label = "color"
    )

    Box(
        modifier = modifier
            .border(1.dp, ExpoColor.gray200, RoundedCornerShape(5.dp))
            .height(32.dp)
            .width(32.dp)
            .drawBehind {
                val cornerRadius = 5.dp.toPx()
                drawRoundRect(
                    color = animatedColor,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
            .clip(RoundedCornerShape(6.dp))
            .expoClickable {
                category =
                    if (category == TrainingCategory.ESSENTIAL) TrainingCategory.CHOICE else TrainingCategory.ESSENTIAL
                onCategoryChange(category)
            }
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            AnimatedVisibility(
                visible = category == TrainingCategory.ESSENTIAL,
                enter = scaleIn(initialScale = 0.5f),
                exit = shrinkOut(shrinkTowards = Alignment.Center)
            ) {
                CheckIcon(tint = ExpoColor.main)
            }
        }
    }
}

@Preview
@Composable
private fun ExpoTrainingSettingBottomSheetPreview() {
    ExpoTrainingSettingBottomSheet(
        onCancelClick = {},
        startedTextState = "",
        endedTextState = "",
        onStartedTextChange = {},
        onEndedTextChange = {},
        onButtonClick = {},
        categoryState = TrainingCategory.ESSENTIAL,
        onCategoryChange = {}
    )
}

@Preview
@Composable
private fun CheckBoxPreview() {
    CustomCheckBox(
        primaryColor = ExpoColor,
        categoryState = TrainingCategory.ESSENTIAL,
        onCategoryChange = {}
    )
}
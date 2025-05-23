package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.common.regex.isValidDateTimeSequence
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.expo.StandardProIdRequestParam
import com.school_of_company.model.param.expo.StandardProRequestParam
import com.school_of_company.ui.keyBoardOption.numberKeyboardOptions
import com.school_of_company.ui.util.filterNonDigits
import com.school_of_company.ui.visualTransformation.DateTimeVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExpoStandardSettingBottomSheet(
    modifier: Modifier = Modifier,
    trainingSettingItem: StandardProRequestParam,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onCancelClick: () -> Unit,
    onButtonClick: () -> Unit,
    onTrainingSettingChange: (StandardProRequestParam) -> Unit,
) {
    val focusManager: FocusManager = LocalFocusManager.current

    var currentItem by remember { mutableStateOf(trainingSettingItem) }

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
                ExpoNoneLineTextField(
                    textState = currentItem.startedAt,
                    lengthLimit = 12,
                    keyboardOptions = numberKeyboardOptions(),
                    visualTransformation = DateTimeVisualTransformation(),
                    placeHolder = {
                        Text(
                            text = "yyyy-MM-dd HH:mm",
                            style = typography.titleBold2,
                            color = colors.gray300,
                            maxLines = 1
                        )
                    },
                    onTextChange = { newText ->
                        currentItem = currentItem.copy(startedAt = newText.filterNonDigits())
                    }
                )

                ExpoNoneLineTextField(
                    textState = currentItem.endedAt,
                    lengthLimit = 12,
                    keyboardOptions = numberKeyboardOptions(),
                    visualTransformation = DateTimeVisualTransformation(),
                    placeHolder = {
                        Text(
                            text = "yyyy-MM-dd HH:mm",
                            style = typography.titleBold2,
                            color = colors.gray300,
                            maxLines = 1
                        )
                    },
                    onTextChange = { newText ->
                        currentItem = currentItem.copy(endedAt = newText.filterNonDigits())
                    }
                )

                ExpoStateButton(
                    text = "확인",
                    onClick = {
                        if (currentItem.startedAt.isValidDateTimeSequence(currentItem.endedAt)) {
                            onTrainingSettingChange(currentItem)
                            onButtonClick()
                        } else {
                            // TODO: 예외 처리
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExpoStandardSettingModifyBottomSheet(
    modifier: Modifier = Modifier,
    trainingSettingItem: StandardProIdRequestParam,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onCancelClick: () -> Unit,
    onButtonClick: () -> Unit,
    onTrainingSettingChange: (StandardProIdRequestParam) -> Unit,
) {
    val focusManager: FocusManager = LocalFocusManager.current
    var currentItem by remember { mutableStateOf(trainingSettingItem) }

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
                ExpoNoneLineTextField(
                    textState = currentItem.startedAt,
                    lengthLimit = 12,
                    keyboardOptions = numberKeyboardOptions(),
                    visualTransformation = DateTimeVisualTransformation(),
                    placeHolder = {
                        Text(
                            text = "yyyy-MM-dd HH:mm",
                            style = typography.titleBold2,
                            color = colors.gray300,
                            maxLines = 1
                        )
                    },
                    onTextChange = { newText ->
                        currentItem = currentItem.copy(startedAt = newText.filterNonDigits())
                    }
                )

                ExpoNoneLineTextField(
                    textState = currentItem.endedAt,
                    lengthLimit = 12,
                    keyboardOptions = numberKeyboardOptions(),
                    visualTransformation = DateTimeVisualTransformation(),
                    placeHolder = {
                        Text(
                            text = "yyyy-MM-dd HH:mm",
                            style = typography.titleBold2,
                            color = colors.gray300,
                            maxLines = 1
                        )
                    },
                    onTextChange = { newText ->
                        currentItem = currentItem.copy(endedAt = newText.filterNonDigits())
                    }
                )

                ExpoStateButton(
                    text = "확인",
                    onClick = {
                        if (currentItem.startedAt.isValidDateTimeSequence(currentItem.endedAt)) {
                            onTrainingSettingChange(currentItem)
                            onButtonClick()
                        } else {
                            // TODO: 예외 처리
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ExpoStandardSettingBottomSheetPreview() {
    ExpoStandardSettingBottomSheet(
        onCancelClick = {},
        trainingSettingItem = StandardProRequestParam(
            title = "",
            startedAt = "",
            endedAt = ""
        ),
        onTrainingSettingChange = {},
        onButtonClick = {}
    )
}
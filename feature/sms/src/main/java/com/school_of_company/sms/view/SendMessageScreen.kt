package com.school_of_company.sms.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.LimitedLengthTextField
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.sms.view.component.HomeSendMessageTopBar
import com.school_of_company.sms.viewmodel.SmsViewModel

@Composable
internal fun SendMessageRoute(
    onBackClick: () -> Unit,
    viewModel: SmsViewModel = hiltViewModel(),
    id: String,
    smsType: String,
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()

    SendMessageScreen(
        onBackClick = onBackClick,
        title = title,
        content = content,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange
    )
}

@Composable
private fun SendMessageScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current
) {
    ExpoAndroidTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(
                    horizontal = 16.dp,
                    top = 61.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                }
        ) {
            HomeSendMessageTopBar(
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    )
                },
                betweenText = "문자 보내기"
            )

            Spacer(modifier = Modifier.padding(top = 28.dp))

            LimitedLengthTextField(
                label = "제목",
                textState = title,
                placeholder = "제목을 입력해주세요.",
                isError = false,
                updateTextValue = onTitleChange,
                lengthLimit = 30,
                overflowErrorMessage = "최대 글자수를 초과했습니다."
            )

            Spacer(modifier = Modifier.padding(top = 18.dp))

            LimitedLengthTextField(
                label = "내용",
                textState = content,
                placeholder = "내용을 입력해주세요.",
                isError = false,
                updateTextValue = onContentChange,
                lengthLimit = 1000,
                overflowErrorMessage = "최대 글자수를 초과했습니다."
            )

            Spacer(modifier = Modifier.weight(1f))

            ExpoStateButton(
                text = "보내기",
                state = if (title.isNotEmpty() && content.isNotEmpty()) ButtonState.Enable else ButtonState.Disable,
                onClick = onBackClick, // Temporary Code
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp)
              ) // {
//                // Add ExpoState CallBack
//            }
        }
    }
}

@Preview
@Composable
private fun SendMessageScreenPreview() {
    SendMessageScreen(
        onBackClick = { /*TODO*/ },
        title = "",
        content = "",
        onTitleChange = {},
        onContentChange = {}
    )
}
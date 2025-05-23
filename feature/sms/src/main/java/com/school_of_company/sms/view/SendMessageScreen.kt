package com.school_of_company.sms.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.LimitedLengthTextField
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.enum.Authority
import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import com.school_of_company.sms.view.component.HomeSendMessageTopBar
import com.school_of_company.sms.viewmodel.SmsViewModel
import com.school_of_company.sms.viewmodel.uiState.SendSmsUiState

@Composable
internal fun SendMessageRoute(
    modifier: Modifier = Modifier,
    id: String,
    smsType: String,
    onBackClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SmsViewModel = hiltViewModel(),
) {
    val sendSmsUiState by viewModel.sendSmsUiState.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()

    LaunchedEffect(sendSmsUiState) {
        when (sendSmsUiState) {
            is SendSmsUiState.Error ->
                onErrorToast(
                    (sendSmsUiState as SendSmsUiState.Error).exception,
                    R.string.sms_send_fail
                )

            SendSmsUiState.Success -> {
                onErrorToast(null, R.string.sms_send_success)
                onBackClick()
            }

            SendSmsUiState.Loading -> Unit
        }
    }

    SendMessageScreen(
        modifier = modifier,
        title = title,
        content = content,
        onBackClick = onBackClick,
        sendRequest = {
            viewModel.sendSmsToParticipantTrainee(
                id = id,
                body = SendSmsToParticipantTraineeParam(
                    title,
                    content,
                    authority = Authority.valueOf(smsType)
                )
            )
        },
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
    )
}

@Composable
private fun SendMessageScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    focusManager: FocusManager = LocalFocusManager.current,
    onBackClick: () -> Unit,
    sendRequest: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(
                    horizontal = 16.dp,
                    top = 68.dp
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
                labelComposable = {},
                value = title,
                placeholder = "제목을 입력해주세요.",
                isError = false,
                updateTextValue = onTitleChange,
                lengthLimit = 30,
                overflowErrorMessage = "최대 글자수를 초과했습니다."
            )

            Spacer(modifier = Modifier.padding(top = 18.dp))

            LimitedLengthTextField(
                label = "내용",
                labelComposable = {},
                value = content,
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
                onClick = sendRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SendMessageScreenPreview() {
    SendMessageScreen(
        title = "",
        content = "",
        onBackClick = {},
        onTitleChange = {},
        onContentChange = {},
        sendRequest = {},
    )
}
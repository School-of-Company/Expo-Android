package com.school_of_company.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.common.regex.checkEmailRegex
import com.school_of_company.common.regex.checkPasswordRegex
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.ExpoDefaultTextField
import com.school_of_company.design_system.icon.ExpoMainLogo
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.signin.viewmodel.SignInViewModel

@Composable
internal fun SignInRoute(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val emailState by viewModel.email.collectAsStateWithLifecycle()
    val passwordState by viewModel.password.collectAsStateWithLifecycle()

    SignInScreen(
        email = emailState,
        password = passwordState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick
    )
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.white)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Spacer(modifier = Modifier.padding(top = 110.dp))

            ExpoMainLogo()

            Text(
                text = "관리자 로그인",
                style = typography.titleBold2,
                color = colors.black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.padding(40.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = "이메일을 입력해주세요.",
                    isError = false,
                    isDisabled = false,
                    errorText = "이메일을 잘못 입력했습니다.",
                    onValueChange = onEmailChange,
                    label = "이메일",
                )

                Spacer(modifier = Modifier.padding(20.dp))

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = "비밀번호를 입력해주세요.",
                    isError = false,
                    isDisabled = false,
                    errorText = "비빌번호를 잘못 입력했습니다.",
                    onValueChange = onPasswordChange,
                    label = "비밀번호"
                )

                Spacer(modifier = Modifier.padding(top = 24.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "아직 관리자 로그인을 안 하셨나요?",
                        style = typography.captionRegular2,
                        color = colors.gray300,
                        fontWeight = FontWeight.Normal
                    )

                    Text(
                        text = " | 회원가입",
                        style = typography.captionRegular2,
                        color = colors.gray500,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.expoClickable { onSignUpClick() }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                ExpoStateButton(
                    text = "로그인",
                    state = if (email.checkEmailRegex() && password.checkPasswordRegex()) ButtonState.Enable else ButtonState.Disable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 28.dp)
                ) {
                    onSignInClick()
                }
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        email = "",
        password = "",
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {},
        onSignUpClick = {}
    )
}
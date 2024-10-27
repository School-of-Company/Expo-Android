package com.school_of_company.signup.view

import android.text.BoringLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.ExpoDefaultTextField
import com.school_of_company.design_system.component.textfield.ExpoNoneLabelTextField
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState

@Composable
internal fun SignUpRoute(
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rePassword by viewModel.rePassword.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val isPasswordValidError by viewModel.isPasswordValidError.collectAsStateWithLifecycle()
    val isPasswordMismatchError by viewModel.isPasswordMismatchError.collectAsStateWithLifecycle()

    DisposableEffect(signUpUiState) {
        when (signUpUiState) {
            is SignUpUiState.Loading -> Unit
            is SignUpUiState.Success -> onSignUpClick()
            is SignUpUiState.PasswordValid -> {
                viewModel.setPasswordValidError(true)
                onErrorToast(null, R.string.expection_password_validdd)
            }
            is SignUpUiState.PasswordMismatch -> {
                viewModel.setPasswordMismatchError(true)
                onErrorToast(null, R.string.mismatch_password)
            }
            is SignUpUiState.DuplicateAccount -> {
                viewModel.setDuplicateAccountError(true)
                onErrorToast(null, R.string.duplication_account_error)
            }
            is SignUpUiState.Error -> {
                viewModel.setError(true)
                onErrorToast((signUpUiState as SignUpUiState.Error).exception, R.string.sign_up_fail)
            }
        }
        onDispose { viewModel.initSignUp() }
    }

    SignUpScreen(
        onBackClick = onBackClick,
        nickname = nickname,
        email = email,
        password = password,
        rePassword = rePassword,
        phoneNumber = phoneNumber,
        onNicknameChange = viewModel::onNicknameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRePasswordChange = viewModel::onRePasswordChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        isPasswordValidError = isPasswordValidError,
        isPasswordMismatchError = isPasswordMismatchError,
        signUpCallBack = {
            viewModel.signUp(
                body = AdminSignUpRequestParam(
                    name = "",
                    nickname = nickname,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )
            )
        }
    )
}

@Composable
internal fun SignUpScreen(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    onBackClick: () -> Unit,
    nickname: String,
    email: String,
    password: String,
    rePassword: String,
    phoneNumber: String,
    onNicknameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRePasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    isPasswordValidError: Boolean,
    isPasswordMismatchError: Boolean,
    signUpCallBack: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {

            LeftArrowIcon(
                modifier = Modifier
                    .expoClickable { onBackClick() }
                    .align(Alignment.Start)
                    .padding(start = 26.dp, top = 88.dp),
                tint = colors.black
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Text(
                text =  stringResource(id = R.string.main_string),
                style = typography.mainTypo,
                color = colors.main
            )

            Text(
                text = stringResource(id = R.string.admin_sign_up),
                style = typography.titleBold2,
                color = colors.black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    label = "이름",
                    placeholder = stringResource(id = R.string.name_hint),
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    onValueChange = onNicknameChange,
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    label = stringResource(id = R.string.email_label),
                    placeholder = stringResource(id = R.string.email_hint),
                    isError = false,
                    isDisabled = false,
                    errorText = stringResource(id = R.string.wrong_email),
                    onValueChange = onEmailChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    label = stringResource(id = R.string.password_label),
                    placeholder = stringResource(id = R.string.password_hint),
                    isError = isPasswordValidError,
                    isDisabled = false,
                    errorText = if (isPasswordValidError) stringResource(id = R.string.expection_password_validdd) else stringResource(id = R.string.wrong_password),
                    onValueChange = onPasswordChange,
                    visualTransformationState = true
                )

                ExpoNoneLabelTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = stringResource(id = R.string.retry_password),
                    isError = isPasswordMismatchError,
                    isDisabled = false,
                    errorText = if (isPasswordMismatchError) stringResource(id = R.string.mismatch_password) else stringResource(id = R.string.wrong_password),
                    onValueChange = onRePasswordChange,
                    visualTransformationState = true
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    label = stringResource(id = R.string.number),
                    placeholder = stringResource(id = R.string.write_number),
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    onValueChange = onPhoneNumberChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ExpoStateButton(
                text = stringResource(id = R.string.check),
                state = if (nickname.isNotBlank() && email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank() && phoneNumber.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingHorizontal(horizontal = 16.dp, bottom = 52.dp)
            ) {
                signUpCallBack()
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        onBackClick = {},
        nickname = "",
        email = "",
        password = "",
        rePassword = "",
        phoneNumber = "",
        onNicknameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onRePasswordChange = {},
        onPhoneNumberChange = {},
        signUpCallBack = {},
        isPasswordMismatchError = false,
        isPasswordValidError = false
    )
}
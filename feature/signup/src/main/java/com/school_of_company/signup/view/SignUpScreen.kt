package com.school_of_company.signup.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import com.school_of_company.design_system.icon.EyeIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import com.school_of_company.signup.view.component.TimeExpoStateButton
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.school_of_company.signup.viewmodel.uistate.SmsSignUpCertificationCodeUiState
import com.school_of_company.signup.viewmodel.uistate.SmsSignUpCertificationSendCodeUiState
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun SignUpRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val smsSignUpCertificationSendCodeUiState by viewModel.smsSignUpCertificationSendCodeUiState.collectAsStateWithLifecycle()
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()
    val smsSignUpCertificationCodeUiState by viewModel.smsSignUpCertificationCodeUiState.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rePassword by viewModel.rePassword.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val certificationNumber by viewModel.certificationNumber.collectAsStateWithLifecycle()
    val isPasswordValidError by viewModel.isPasswordValidError.collectAsStateWithLifecycle()
    val isPasswordMismatchError by viewModel.isPasswordMismatchError.collectAsStateWithLifecycle()
    val isEmailValidError by viewModel.isEmailValidError.collectAsStateWithLifecycle()
    val isCertificationCodeError by viewModel.isCertificationCodeValid.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(signUpUiState) {
        when (signUpUiState) {
            is SignUpUiState.Loading -> Unit
            is SignUpUiState.Success -> {
                onSignUpClick()
                makeToast(context, "회원가입 성공, 다시 로그인 해주세요.")
            }

            is SignUpUiState.Error -> {
                viewModel.setError(true)
                onErrorToast((signUpUiState as SignUpUiState.Error).exception,null)
            }

            is SignUpUiState.Conflict -> {
                viewModel.setError(true)
                onErrorToast(null, R.string.conflict_info)
            }

            is SignUpUiState.NotSmsCheck -> {
                viewModel.setError(true)
                onErrorToast(null, R.string.not_sms_check)
            }
        }
    }

    LaunchedEffect(smsSignUpCertificationCodeUiState){
        when(smsSignUpCertificationCodeUiState){
            is SmsSignUpCertificationCodeUiState.Loading -> Unit

            is SmsSignUpCertificationCodeUiState.Success -> {
                makeToast(context, "인증을 성공했습니다.")
            }

            is SmsSignUpCertificationCodeUiState.Error -> {
                val error = smsSignUpCertificationCodeUiState as SmsSignUpCertificationCodeUiState.Error
                onErrorToast(error.exception, error.messageResId)
            }
        }
    }

    SignUpScreen(
        modifier = modifier,
        isPasswordValidError = isPasswordValidError,
        isPasswordMismatchError = isPasswordMismatchError,
        isEmailValidError = isEmailValidError,
        isCertificationCodeError = isCertificationCodeError,
        name = name,
        nickname = nickname,
        email = email,
        password = password,
        rePassword = rePassword,
        phoneNumber = phoneNumber,
        certificationNumber = certificationNumber,
        onBackClick = onBackClick,
        signUpCallBack = {
            viewModel.signUp(
                body = AdminSignUpRequestParam(
                    name = name,
                    nickname = nickname,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )
            )
        },
        certificationCallBack = {
            viewModel.certificationCode(
                phoneNumber = phoneNumber,
                certificationNumber = certificationNumber
            )
        },
        sendCertificationCodeCallBack = {
            viewModel.sendCertificationCode(
                body = SmsSignUpCertificationNumberSendRequestParam(
                    phoneNumber = phoneNumber
                )
            )
            viewModel.setCertificationResent(true)
        },
        onNameChange = viewModel::onNameChange,
        onNicknameChange = viewModel::onNicknameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRePasswordChange = viewModel::onRePasswordChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onCertificationNumberChange = viewModel::onCertificationNumberChange,
        smsSignUpCertificationSendCodeUiState = smsSignUpCertificationSendCodeUiState,
        smsSignUpCertificationCodeUiState = smsSignUpCertificationCodeUiState
    )
}

@Composable
private fun SignUpScreen(
    modifier: Modifier = Modifier,
    isPasswordValidError: Boolean,
    isPasswordMismatchError: Boolean,
    isEmailValidError: Boolean,
    smsSignUpCertificationCodeUiState: SmsSignUpCertificationCodeUiState,
    smsSignUpCertificationSendCodeUiState: SmsSignUpCertificationSendCodeUiState,
    isCertificationCodeError: Boolean,
    name: String,
    nickname: String,
    email: String,
    password: String,
    rePassword: String,
    phoneNumber: String,
    certificationNumber: String,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onBackClick: () -> Unit,
    signUpCallBack: () -> Unit,
    certificationCallBack: () -> Unit,
    sendCertificationCodeCallBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRePasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onCertificationNumberChange: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isCheckPasswordVisible by remember { mutableStateOf(false) }
    var isFirstAttempt by remember { mutableStateOf(true) }
    val isSuccess = smsSignUpCertificationSendCodeUiState is SmsSignUpCertificationSendCodeUiState.Success


    ExpoAndroidTheme { colors, typography ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .background(color = colors.white)
                .verticalScroll(scrollState)
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
                text = stringResource(id = R.string.main_string),
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
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = name,
                    label = stringResource(id = R.string.name_label),
                    placeholder = stringResource(id = R.string.name_hint),
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    onValueChange = onNameChange,
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = nickname,
                    label = stringResource(id = R.string.nickname_label),
                    placeholder = stringResource(id = R.string.hint_nickname),
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    onValueChange = onNicknameChange
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = email,
                    label = stringResource(id = R.string.email_label),
                    placeholder = stringResource(id = R.string.email_hint),
                    isError = isEmailValidError,
                    isDisabled = false,
                    errorText = stringResource(id = R.string.wrong_email),
                    onValueChange = onEmailChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                ExpoDefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = password,
                    label = stringResource(id = R.string.password_label),
                    placeholder = "(8~24자 영어(대소문자)/숫자 특수문자 1개이상)",
                    isError = isPasswordValidError,
                    isDisabled = false,
                    errorText = if (isPasswordValidError) stringResource(id = R.string.expection_password_validdd) else stringResource(
                        id = R.string.wrong_password
                    ),
                    onValueChange = onPasswordChange,
                    visualTransformationState = if (isPasswordVisible) false else true,
                    trailingIcon = {
                        EyeIcon(
                            isSelected = isPasswordVisible,
                            modifier = Modifier.expoClickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    }
                )

                ExpoNoneLabelTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = rePassword,
                    placeholder = stringResource(id = R.string.retry_password),
                    isError = isPasswordMismatchError,
                    isDisabled = false,
                    errorText = if (isPasswordMismatchError) stringResource(id = R.string.mismatch_password) else stringResource(
                        id = R.string.wrong_password
                    ),
                    onValueChange = onRePasswordChange,
                    visualTransformationState = if (isCheckPasswordVisible) false else true,
                    trailingIcon = {
                        EyeIcon(
                            isSelected = isCheckPasswordVisible,
                            modifier = Modifier.expoClickable {
                                isCheckPasswordVisible = !isCheckPasswordVisible
                            }
                        )
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                    verticalAlignment = Alignment.Bottom
                ) {
                    ExpoDefaultTextField(
                        modifier = Modifier
                            .height(50.dp)
                            .width(242.dp),
                        value = phoneNumber,
                        label = stringResource(id = R.string.number),
                        placeholder = "연락처는 \" - \" 빼고 입력해주세요",
                        isError = false,
                        isDisabled = false,
                        errorText = "",
                        onValueChange = onPhoneNumberChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    if (isSuccess || !isFirstAttempt) {
                        TimeExpoStateButton (
                            modifier = Modifier.fillMaxWidth(),
                            text = "재발송"
                        ){
                            sendCertificationCodeCallBack()
                        }
                    } else {
                        ExpoStateButton(
                            text = "인증번호",
                            state = if (phoneNumber.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            sendCertificationCodeCallBack()
                            isFirstAttempt = false
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ExpoNoneLabelTextField(
                        modifier = Modifier
                            .height(50.dp)
                            .width(242.dp),
                        value = certificationNumber,
                        placeholder = stringResource(id = R.string.write_certification),
                        isError = isCertificationCodeError,
                        isDisabled = false,
                        errorText = stringResource(id = R.string.valid_certification),
                        onValueChange = {
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) onCertificationNumberChange(
                                it
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    ExpoStateButton(
                        text = stringResource(id = R.string.check),
                        state = when {
                            smsSignUpCertificationCodeUiState is SmsSignUpCertificationCodeUiState.Success -> ButtonState.Disable
                            certificationNumber.isNotBlank() -> ButtonState.Enable
                            else -> ButtonState.Disable
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        certificationCallBack()
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ExpoStateButton(
                text = stringResource(id = R.string.check_sign_up),
                state = if (name.isNotBlank() && nickname.isNotBlank() && email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank() && phoneNumber.isNotBlank() && certificationNumber.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingHorizontal(horizontal = 16.dp, bottom = 52.dp, top = 30.dp)
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
        name = "",
        nickname = "",
        email = "",
        password = "",
        rePassword = "",
        phoneNumber = "",
        certificationNumber = "",
        onNameChange = {},
        onNicknameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onRePasswordChange = {},
        onPhoneNumberChange = {},
        signUpCallBack = {},
        onCertificationNumberChange = {},
        isPasswordMismatchError = false,
        isPasswordValidError = false,
        isEmailValidError = false,
        certificationCallBack = {},
        sendCertificationCodeCallBack = {},
        isCertificationCodeError = false,
        smsSignUpCertificationSendCodeUiState = SmsSignUpCertificationSendCodeUiState.Loading,
        smsSignUpCertificationCodeUiState = SmsSignUpCertificationCodeUiState.Loading
    )
}
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
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
import com.school_of_company.design_system.component.textfield.ExpoDefaultTextField
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.signin.viewmodel.SignInViewModel
import com.school_of_company.signin.viewmodel.uistate.SaveTokenUiState
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun SignInRoute(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()
    val saveTokenUiState by viewModel.savedTokenUiState.collectAsStateWithLifecycle()
    val emailState by viewModel.email.collectAsStateWithLifecycle()
    val passwordState by viewModel.password.collectAsStateWithLifecycle()
    val isEmailError by viewModel.isEmailError.collectAsStateWithLifecycle()
    val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DisposableEffect(signInUiState, saveTokenUiState) {
        when (signInUiState) {
            is SignInUiState.Loading -> Unit
            is SignInUiState.Success -> {
                when (saveTokenUiState) {
                    is SaveTokenUiState.Loading -> Unit
                    is SaveTokenUiState.Success -> {
                        onSignInClick()
                        makeToast(context, "로그인 성공")
                    }
                    is SaveTokenUiState.Error -> {
                        viewModel.setError(true)
                        onErrorToast((saveTokenUiState as SaveTokenUiState.Error).exception, R.string.expection_saveToken)
                    }
                }
            }
            is SignInUiState.NotFound -> {
                viewModel.setNotFoundError(true)
                onErrorToast(null, R.string.expection_not_found)
            }
            is SignInUiState.EmailNotValid -> {
                viewModel.setEmailError(true)
                onErrorToast(null, R.string.expection_email_not_valid)
            }
            is SignInUiState.PasswordValid -> {
                viewModel.setPasswordError(true)
                onErrorToast(null, R.string.expection_password_valid)
            }
            is SignInUiState.BadRequest -> {
                viewModel.setBadRequestError(true)
                onErrorToast(null, R.string.expection_bad_request)
            }
            is SignInUiState.Error -> {
                viewModel.setError(true)
                onErrorToast((signInUiState as SignInUiState.Error).exception,  R.string.expection_signIn)
            }
        }
        onDispose {  }
    }

    SignInScreen(
        isEmailError = isEmailError,
        isPasswordError = isPasswordError,
        email = emailState,
        password = passwordState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignUpClick = onSignUpClick,
        signInCallBack =  {
            viewModel.signIn(body = AdminSignInRequestParam(
                nickname = emailState,
                password = passwordState
            ))
        },
    )
}

@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    signInCallBack: () -> Unit,
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
            Spacer(modifier = modifier.padding(top = 150.dp))

            Text(
                text =  stringResource(id = R.string.main_string),
                style = typography.mainTypo,
                color = colors.main
            )

            Text(
                text = stringResource(id = R.string.admin_sign_in),
                style = typography.titleBold2,
                color = colors.black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = modifier.padding(40.dp))

            Column(
                modifier = modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExpoDefaultTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = stringResource(id = R.string.id_hint),
                    isError = isEmailError,
                    isDisabled = false,
                    errorText = stringResource(id = R.string.wrong_id),
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.id),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = modifier.padding(20.dp))

                ExpoDefaultTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = stringResource(id = R.string.password_hint),
                    isError = isPasswordError,
                    isDisabled = false,
                    errorText = stringResource(id = R.string.wrong_password),
                    onValueChange = onPasswordChange,
                    label = stringResource(id = R.string.password_label),
                    visualTransformationState = true
                )

                Spacer(modifier = modifier.padding(top = 24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.not_member),
                        style = typography.captionRegular2,
                        color = colors.gray300,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier
                        .width(1.dp)
                        .height(12.dp)
                        .background(color = colors.gray400)
                    )

                    Text(
                        text = stringResource(id = R.string.sign_out_bar),
                        style = typography.captionRegular2,
                        color = colors.gray500,
                        fontWeight = FontWeight.Normal,
                        modifier = modifier.expoClickable { onSignUpClick() }
                    )
                }

                Spacer(modifier = modifier.weight(1f))

                ExpoStateButton(
                    text = stringResource(id = R.string.sign_in),
                    state = if (email.isNotBlank() && password.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 52.dp)
                ) {
                    signInCallBack()
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
        onSignUpClick = {},
        signInCallBack = {},
        isEmailError = false,
        isPasswordError = false
    )
}
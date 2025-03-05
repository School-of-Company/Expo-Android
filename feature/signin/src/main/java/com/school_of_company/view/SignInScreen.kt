package com.school_of_company.view

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.ExpoDefaultTextField
import com.school_of_company.design_system.icon.EyeIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.signin.viewmodel.SignInViewModel
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()
    val idState by viewModel.id.collectAsStateWithLifecycle()
    val passwordState by viewModel.password.collectAsStateWithLifecycle()
    val isEmailError by viewModel.isEmailError.collectAsStateWithLifecycle()
    val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            viewModel.initSignIn()
        }
    }

    DisposableEffect(signInUiState) {
        when (signInUiState) {
            is SignInUiState.Loading -> Unit
            is SignInUiState.Success -> {
                onSignInClick()
                makeToast(context, "로그인 성공")
            }

            is SignInUiState.NotFound -> {
                viewModel.setNotFoundError(true)
                onErrorToast(null, R.string.expection_not_found)
            }

            is SignInUiState.EmailNotValid -> {
                viewModel.setIdError(true)
                onErrorToast(null, R.string.expection_id_not_valid)
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
                onErrorToast(
                    (signInUiState as SignInUiState.Error).exception,
                    R.string.expection_signIn
                )
            }
        }
        onDispose { }
    }

    SignInScreen(
        modifier = modifier,
        isEmailError = isEmailError,
        isPasswordError = isPasswordError,
        id = idState,
        password = passwordState,
        onSignUpClick = onSignUpClick,
        signInCallBack = {
            viewModel.signIn(
                body = AdminSignInRequestParam(
                    nickname = idState,
                    password = passwordState
                )
            )
        },
        onIdChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    id: String,
    password: String,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onSignUpClick: () -> Unit,
    signInCallBack: () -> Unit,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 16.dp)
                .imePadding()
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = modifier.padding(top = 150.dp))

                Text(
                    text = stringResource(id = R.string.main_string),
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
                ExpoDefaultTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    placeholder = stringResource(id = R.string.id_hint),
                    isError = isEmailError,
                    isDisabled = false,
                    errorText = stringResource(id = R.string.wrong_id),
                    onValueChange = onIdChange,
                    label = stringResource(id = R.string.id),
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

                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(12.dp)
                            .background(color = colors.gray400)
                    )

                    Text(
                        text = stringResource(id = R.string.sign_out_bar),
                        style = typography.captionRegular2,
                        color = colors.main,
                        fontWeight = FontWeight.Normal,
                        modifier = modifier.expoClickable { onSignUpClick() }
                    )
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            ExpoStateButton(
                text = stringResource(id = R.string.sign_in),
                state = if (id.isNotBlank() && password.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 52.dp)
            ) {
                signInCallBack()
            }
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        id = "",
        password = "",
        onIdChange = {},
        onPasswordChange = {},
        onSignUpClick = {},
        signInCallBack = {},
        isEmailError = false,
        isPasswordError = false
    )
}
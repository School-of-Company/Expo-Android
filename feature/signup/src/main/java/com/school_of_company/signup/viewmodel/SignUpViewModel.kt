package com.school_of_company.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.network.errorHandling
import com.school_of_company.common.regex.checkEmailRegex
import com.school_of_company.common.regex.checkPasswordRegex
import com.school_of_company.domain.usecase.auth.AdminSignUpRequestUseCase
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val signUpRequestUseCase: AdminSignUpRequestUseCase
) : ViewModel() {
    companion object {
        private const val NICKNAME = "nickname"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val RE_PASSWORD = "rePassword"
        private const val PHONE_NUMBER = "phoneNumber"
    }
    private var _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpUiState = _signUpUiState.asStateFlow()

    internal var nickname = savedStateHandle.getStateFlow(key = NICKNAME, initialValue = "")

    internal var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")

    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    internal var rePassword = savedStateHandle.getStateFlow(key = RE_PASSWORD, initialValue = "")

    internal var phoneNumber = savedStateHandle.getStateFlow(key = PHONE_NUMBER, initialValue = "")

    private var _isError = MutableStateFlow(false)

    private var _isEmailValidError = MutableStateFlow(false)
    internal val isEmailValidError: StateFlow<Boolean> = _isEmailValidError.asStateFlow()

    private var _isPasswordValidError = MutableStateFlow(false)
    internal val isPasswordValidError: StateFlow<Boolean> = _isPasswordValidError.asStateFlow()

    private var _isPasswordMismatchError = MutableStateFlow(false)
    internal val isPasswordMismatchError: StateFlow<Boolean> = _isPasswordMismatchError.asStateFlow()

    private var _isDuplicateAccountError = MutableStateFlow(false)

    internal fun setError(value: Boolean) {
        _isError.value = value
    }

    internal fun setEmailValidError(value: Boolean) {
        _isEmailValidError.value = value
    }

    internal fun setPasswordValidError(value: Boolean) {
        _isPasswordValidError.value = value
    }

    internal fun setPasswordMismatchError(value: Boolean) {
        _isPasswordMismatchError.value = value
    }

    internal fun setDuplicateAccountError(value: Boolean) {
        _isDuplicateAccountError.value = value
    }

    internal fun initSignUp() {
        _signUpUiState.value = SignUpUiState.Loading
    }

    internal fun signUp(body: AdminSignUpRequestParam) = viewModelScope.launch {
        setError(false)
        setEmailValidError(false)
        setPasswordValidError(false)
        setPasswordMismatchError(false)
        setDuplicateAccountError(false)
        _signUpUiState.value = SignUpUiState.Loading
        when {
            !email.value.checkEmailRegex() -> {
                _signUpUiState.value = SignUpUiState.EmailValid
                setEmailValidError(true)
            }

            password.value != rePassword.value -> {
                _signUpUiState.value = SignUpUiState.PasswordMismatch
                setPasswordMismatchError(true)
            }
            !password.value.checkPasswordRegex() -> {
                _signUpUiState.value = SignUpUiState.PasswordValid
                setPasswordValidError(true)
            }
            else -> {
                signUpRequestUseCase(body = body)
                    .onSuccess {
                        it.catch { remoteError ->
                            _signUpUiState.value = SignUpUiState.Error(remoteError)
                            remoteError.errorHandling {
                                conflictAction = { _signUpUiState.value = SignUpUiState.DuplicateAccount }
                            }
                        }.collect { _signUpUiState.value = SignUpUiState.Success }
                    }
                    .onFailure { error ->
                        _signUpUiState.value = SignUpUiState.Error(error)
                        error.errorHandling {
                            conflictAction = { _signUpUiState.value = SignUpUiState.DuplicateAccount }
                        }
                    }
            }
        }
    }

    internal fun onNicknameChange(value: String) { savedStateHandle[NICKNAME] = value }

    internal fun onEmailChange(value: String) { savedStateHandle[EMAIL] = value }

    internal fun onPasswordChange(value: String) { savedStateHandle[PASSWORD] = value }

    internal fun onRePasswordChange(value: String) { savedStateHandle[RE_PASSWORD] = value }

    internal fun onPhoneNumberChange(value: String) { savedStateHandle[PHONE_NUMBER] = value }
}
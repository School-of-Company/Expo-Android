package com.school_of_company.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.regex.checkEmailRegex
import com.school_of_company.common.regex.checkPasswordRegex
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.auth.AdminSignUpRequestUseCase
import com.school_of_company.domain.usecase.sms.SmsSignUpCertificationNumberCertificationRequestUseCase
import com.school_of_company.domain.usecase.sms.SmsSignUpCertificationNumberSendRequestUseCase
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import com.school_of_company.design_system.R
import com.school_of_company.signup.viewmodel.uistate.SignUpUiState
import com.school_of_company.signup.viewmodel.uistate.SmsSignUpCertificationCodeUiState
import com.school_of_company.signup.viewmodel.uistate.SmsSignUpCertificationSendCodeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val signUpRequestUseCase: AdminSignUpRequestUseCase,
    private val smsSignUpCertificationNumberSendRequestUseCase: SmsSignUpCertificationNumberSendRequestUseCase,
    private val smsSignUpCertificationNumberCertificationRequestUseCase: SmsSignUpCertificationNumberCertificationRequestUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val NAME = "name"
        private const val NICKNAME = "nickname"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val RE_PASSWORD = "rePassword"
        private const val PHONE_NUMBER = "phoneNumber"
        private const val CERTIFICATION_NUMBER = "certificationNumber"
    }

    private var _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpUiState = _signUpUiState.asStateFlow()

    private var _smsSignUpCertificationSendCodeUiState = MutableStateFlow<SmsSignUpCertificationSendCodeUiState>(SmsSignUpCertificationSendCodeUiState.Loading)
    internal val smsSignUpCertificationSendCodeUiState = _smsSignUpCertificationSendCodeUiState.asStateFlow()

    // TODO: 사용 여부 확인 
    private var _smsSignUpCertificationCodeUiState = MutableStateFlow<SmsSignUpCertificationCodeUiState>(SmsSignUpCertificationCodeUiState.Loading)
    internal val smsSignUpCertificationCodeUiState = _smsSignUpCertificationCodeUiState.asStateFlow()

    internal var name = savedStateHandle.getStateFlow(key = NAME, initialValue = "")

    internal var nickname = savedStateHandle.getStateFlow(key = NICKNAME, initialValue = "")

    internal var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")

    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    internal var rePassword = savedStateHandle.getStateFlow(key = RE_PASSWORD, initialValue = "")

    internal var phoneNumber = savedStateHandle.getStateFlow(key = PHONE_NUMBER, initialValue = "")

    internal var certificationNumber = savedStateHandle.getStateFlow(key = CERTIFICATION_NUMBER, initialValue = "")

    private var _isError = MutableStateFlow(false)

    private var _isCodeError = MutableStateFlow(false)

    private var _isEmailValidError = MutableStateFlow(false)
    internal val isEmailValidError: StateFlow<Boolean> = _isEmailValidError.asStateFlow()

    private var _isPasswordValidError = MutableStateFlow(false)
    internal val isPasswordValidError: StateFlow<Boolean> = _isPasswordValidError.asStateFlow()

    private var _isPasswordMismatchError = MutableStateFlow(false)
    internal val isPasswordMismatchError: StateFlow<Boolean> = _isPasswordMismatchError.asStateFlow()

    private var _isDuplicateAccountError = MutableStateFlow(false)

    private var _isCertificationCodeValid = MutableStateFlow(false)
    internal val isCertificationCodeValid: StateFlow<Boolean> = _isCertificationCodeValid.asStateFlow()

    private val _isCertificationResent = MutableStateFlow(false)
    internal val isCertificationResent: StateFlow<Boolean> = _isCertificationResent.asStateFlow()

    private var _isUnauthorizedError = MutableStateFlow(false)

    private var _isBadRequestError = MutableStateFlow(false)

    private fun setCodeError(value: Boolean) {
        _isCodeError.value = value
    }

    internal fun setError(value: Boolean) {
        _isError.value = value
    }

    internal fun setUnauthorizedError(value: Boolean) {
        _isUnauthorizedError.value = value
    }

    internal fun setBadRequestError(value: Boolean) {
        _isBadRequestError.value = value
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

    internal fun setCertificationResent(value: Boolean) {
        _isCertificationResent.value = value
    }

    internal fun signUp(body: AdminSignUpRequestParam) = viewModelScope.launch {
        setError(false)
        setEmailValidError(false)
        setPasswordValidError(false)
        setPasswordMismatchError(false)
        setDuplicateAccountError(false)
        when {
            !email.value.checkEmailRegex() -> {
                setEmailValidError(true)
            }

            password.value != rePassword.value -> {
                setPasswordMismatchError(true)
            }

            !password.value.checkPasswordRegex() -> {
                setPasswordValidError(true)
            }

            else -> {
                signUpRequestUseCase(body = body)
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> _signUpUiState.value = SignUpUiState.Loading
                            is Result.Success -> _signUpUiState.value = SignUpUiState.Success
                            is Result.Error -> {
                                val exception = result.exception
                                _signUpUiState.value = when {
                                    exception is HttpException -> when (exception.code()) {
                                        409 -> SignUpUiState.Conflict
                                        404  -> SignUpUiState.NotSmsCheck
                                        else -> SignUpUiState.Error(exception)
                                    }

                                    else -> SignUpUiState.Error(exception)
                                }
                            }
                        }
                    }
            }
        }
    }

    internal fun certificationCode(phoneNumber: String, certificationNumber: String) =
        viewModelScope.launch {
            setCodeError(false)
            smsSignUpCertificationNumberCertificationRequestUseCase(
                phoneNumber = phoneNumber,
                code = certificationNumber
            )
                .onSuccess {
                    it.catch { exception ->
                        _smsSignUpCertificationCodeUiState.value = when {
                            exception is HttpException -> when (exception.code()) {
                                401 -> {
                                    setUnauthorizedError(true)
                                    SmsSignUpCertificationCodeUiState.Error(
                                        exception,
                                        R.string.valid_certification,
                                        SmsSignUpCertificationCodeUiState.ErrorType.Unauthorized
                                    )
                                }
                                404 -> {
                                    setBadRequestError(true)
                                    SmsSignUpCertificationCodeUiState.Error(
                                        exception,
                                        R.string.sms_not_certification,
                                        SmsSignUpCertificationCodeUiState.ErrorType.BAD_REQUEST
                                    )
                                }
                                else -> {
                                    setError(true)
                                    SmsSignUpCertificationCodeUiState.Error(
                                        exception,
                                        R.string.fail_certification,
                                        SmsSignUpCertificationCodeUiState.ErrorType.GENERAL
                                    )
                                }
                            }
                            else -> {
                                setError(true)
                                SmsSignUpCertificationCodeUiState.Error(
                                    exception,
                                    R.string.fail_certification,
                                    SmsSignUpCertificationCodeUiState.ErrorType.GENERAL
                                )
                            }
                        }
                    }.collect { _smsSignUpCertificationCodeUiState.value = SmsSignUpCertificationCodeUiState.Success }
                }
                .onFailure { error ->
                    _smsSignUpCertificationCodeUiState.value = SmsSignUpCertificationCodeUiState.Error(
                        error,
                        R.string.fail_certification,
                        SmsSignUpCertificationCodeUiState.ErrorType.GENERAL
                    )
                }
        }

    internal fun sendCertificationCode(body: SmsSignUpCertificationNumberSendRequestParam) =
        viewModelScope.launch {
            _smsSignUpCertificationSendCodeUiState.value = SmsSignUpCertificationSendCodeUiState.Loading
            smsSignUpCertificationNumberSendRequestUseCase(body = body)
                .onSuccess {
                    it.catch { remoteError ->
                        _smsSignUpCertificationSendCodeUiState.value = SmsSignUpCertificationSendCodeUiState.Error(remoteError)
                    }.collect { _smsSignUpCertificationSendCodeUiState.value = SmsSignUpCertificationSendCodeUiState.Success }
                }
                .onFailure { error ->
                    _smsSignUpCertificationSendCodeUiState.value = SmsSignUpCertificationSendCodeUiState.Error(error)
                }
        }

    internal fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    internal fun onNicknameChange(value: String) {
        savedStateHandle[NICKNAME] = value
    }

    internal fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    internal fun onRePasswordChange(value: String) {
        savedStateHandle[RE_PASSWORD] = value
    }

    internal fun onPhoneNumberChange(value: String) {
        savedStateHandle[PHONE_NUMBER] = value
    }

    internal fun onCertificationNumberChange(value: String) {
        savedStateHandle[CERTIFICATION_NUMBER] = value
    }
}
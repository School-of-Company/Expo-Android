package com.school_of_company.signin.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.regex.checkPasswordRegex
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.design_system.R
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val ID = "id"
        private const val PASSWORD = "password"
    }

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    internal val signInUiState = _signInUiState.asStateFlow()

    internal var id = savedStateHandle.getStateFlow(key = ID, initialValue = "")

    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private var _isError = MutableStateFlow(false)

    private val _isPasswordError = MutableStateFlow(false)
    internal val isPasswordError: StateFlow<Boolean> = _isPasswordError.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    internal val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    private val _isNotFoundError = MutableStateFlow(false)

    private val _isBadRequestError = MutableStateFlow(false)

    private val _isForbiddenError = MutableStateFlow(false)

    internal fun setForbiddenError(value: Boolean) {
        _isForbiddenError.value = value
    }

    internal fun setPasswordError(value: Boolean) {
        _isPasswordError.value = value
    }

    internal fun setError(value: Boolean) {
        _isError.value = value
    }

    internal fun setIdError(value: Boolean) {
        _isEmailError.value = value
    }

    internal fun setNotFoundError(value: Boolean) {
        _isNotFoundError.value = value
    }

    internal fun setBadRequestError(value: Boolean) {
        _isBadRequestError.value = value
    }

    internal fun signIn(body: AdminSignInRequestParam) = viewModelScope.launch {
        _signInUiState.value = SignInUiState.Loading
        initSignIn()

        if (!password.value.checkPasswordRegex()) {
            _signInUiState.value = SignInUiState.Error(
                messageResId = R.string.expection_password_valid,
                errorType = SignInUiState.ErrorType.PASSWORD
            )
            setPasswordError(true)
            return@launch
        }

            authRepository.adminSignIn(body = body)
                .asResult()
                .collectLatest { result->
                    when (result) {
                        is Result.Loading -> _signInUiState.value = SignInUiState.Loading
                        is Result.Success -> {
                            _signInUiState.value = SignInUiState.Success
                            authRepository.saveToken(result.data)
                        }
                        is Result.Error -> {
                            val exception = result.exception
                            _signInUiState.value = when {
                                exception is HttpException -> when (exception.code()) {
                                    400 -> {
                                        setBadRequestError(true)
                                        SignInUiState.Error(exception, R.string.expection_bad_request, SignInUiState.ErrorType.BAD_REQUEST)
                                    }
                                    403 -> {
                                        setForbiddenError(true)
                                        SignInUiState.Error(exception, R.string.exception_forbidden, SignInUiState.ErrorType.FORBIDDEN)
                                    }
                                    404 -> {
                                        setNotFoundError(true)
                                        SignInUiState.Error(exception, R.string.expeciton_not_found, SignInUiState.ErrorType.NOT_FOUND)
                                    }
                                    500 -> {
                                        setError(true)
                                        SignInUiState.Error(exception, R.string.expection_server_error, SignInUiState.ErrorType.SERVER)
                                    }
                                    else -> {
                                        setError(true)
                                        SignInUiState.Error(exception, R.string.expection_signIn, SignInUiState.ErrorType.GENERAL)
                                    }
                                }

                                else -> {
                                    setError(true)
                                    SignInUiState.Error(exception, R.string.expection_signIn, SignInUiState.ErrorType.GENERAL)
                                }
                            }
                        }
                    }
                }
    }

    internal fun initSignIn() {
        setError(false)
        setIdError(false)
        setPasswordError(false)
        setNotFoundError(false)
        setBadRequestError(false)
        setForbiddenError(false)
        _signInUiState.value = SignInUiState.Loading
    }

    internal fun onIdChange(value: String) {
        savedStateHandle[ID] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
}


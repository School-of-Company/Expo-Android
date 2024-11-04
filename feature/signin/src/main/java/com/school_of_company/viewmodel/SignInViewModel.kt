package com.school_of_company.signin.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.network.errorHandling
import com.school_of_company.common.regex.checkPasswordRegex
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.auth.AdminSignInRequestUseCase
import com.school_of_company.domain.usecase.auth.SaveTokenUseCase
import com.school_of_company.model.model.auth.AdminTokenResponseModel
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.signin.viewmodel.uistate.SaveTokenUiState
import com.school_of_company.signin.viewmodel.uistate.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: AdminSignInRequestUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }

    private var _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    internal val signInUiState = _signInUiState.asStateFlow()

    private var _savedTokenUiState = MutableStateFlow<SaveTokenUiState>(SaveTokenUiState.Loading)
    internal val savedTokenUiState = _savedTokenUiState.asStateFlow()

    internal var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")

    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private var _isError = MutableStateFlow(false)

    private var _isPasswordError = MutableStateFlow(false)
    internal val isPasswordError: StateFlow<Boolean> = _isPasswordError.asStateFlow()

    private var _isEmailError = MutableStateFlow(false)
    internal val isEmailError: StateFlow<Boolean> = _isEmailError.asStateFlow()

    private var _isNotFoundError = MutableStateFlow(false)
    internal val isNotFoundError: StateFlow<Boolean> = _isNotFoundError.asStateFlow()

    private var _isBadRequestError = MutableStateFlow(false)
    internal val isBadRequestError: StateFlow<Boolean> = _isBadRequestError.asStateFlow()

    internal fun setPasswordError(value: Boolean) {
        _isPasswordError.value = value
    }

    internal fun setError(value: Boolean) {
        _isError.value = value
    }

    internal fun setEmailError(value: Boolean) {
        _isEmailError.value = value
    }

    internal fun setNotFoundError(value: Boolean) {
        _isNotFoundError.value = value
    }

    internal fun setBadRequestError(value: Boolean) {
        _isBadRequestError.value = value
    }

    internal fun signIn(body: AdminSignInRequestParam) = viewModelScope.launch {
        setError(false)
        setEmailError(false)
        setPasswordError(false)
        if (!password.value.checkPasswordRegex()) {
            _signInUiState.value = SignInUiState.PasswordValid
        } else {
            signInUseCase(body = body)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            _signInUiState.value = SignInUiState.Loading
                        }

                        is Result.Success -> {
                            _signInUiState.value = SignInUiState.Success(result.data)
                            saveToken(result.data)
                        }

                        is Result.Error -> {
                            _signInUiState.value = SignInUiState.Error(result.exception)
                            result.exception.errorHandling {
                                badRequestAction = { _signInUiState.value = SignInUiState.BadRequest }
                                notFoundAction = { _signInUiState.value = SignInUiState.NotFound }
                            }
                        }
                    }
                }
        }
    }

    private fun saveToken(token: AdminTokenResponseModel) = viewModelScope.launch {
        _savedTokenUiState.value = SaveTokenUiState.Loading
        saveTokenUseCase(token = token)
            .onSuccess {
                _savedTokenUiState.value = SaveTokenUiState.Success
            }
            .onFailure { remoteError ->
                _savedTokenUiState.value = SaveTokenUiState.Error(remoteError)
                setError(true)
            }
    }

    internal fun onEmailChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    internal fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }
}


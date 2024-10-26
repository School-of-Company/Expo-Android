package com.school_of_company.signin.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.network.errorHandling
import com.school_of_company.common.regex.checkEmailRegex
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

    internal fun signIn(body: AdminSignInRequestParam) = viewModelScope.launch {
        if (email.value.checkEmailRegex()) {
            _signInUiState.value = SignInUiState.EmailNotValid
        } else {
            signInUseCase(body = body)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> { _signInUiState.value = SignInUiState.Loading }
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
            }
    }

    internal var email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    internal var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    internal fun onEmailChange(value: String) { savedStateHandle[EMAIL] = value }

    internal fun onPasswordChange(value: String) { savedStateHandle[PASSWORD] = value }
}


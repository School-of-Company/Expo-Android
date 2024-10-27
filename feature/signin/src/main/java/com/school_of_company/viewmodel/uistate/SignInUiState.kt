package com.school_of_company.signin.viewmodel.uistate

import com.school_of_company.model.model.auth.AdminTokenResponseModel

sealed interface SignInUiState {
    object Loading : SignInUiState
    data class Success(val signInResponseModel: AdminTokenResponseModel) : SignInUiState
    data class Error(val exception: Throwable) : SignInUiState
    object BadRequest : SignInUiState
    object NotFound : SignInUiState
    object EmailNotValid : SignInUiState
    object PasswordValid : SignInUiState
}
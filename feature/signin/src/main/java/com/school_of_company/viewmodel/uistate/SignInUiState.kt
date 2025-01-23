package com.school_of_company.signin.viewmodel.uistate

sealed interface SignInUiState {
    object Loading : SignInUiState
    object Success : SignInUiState
    data class Error(val exception: Throwable) : SignInUiState
    object BadRequest : SignInUiState
    object NotFound : SignInUiState
    object EmailNotValid : SignInUiState
    object PasswordValid : SignInUiState
}
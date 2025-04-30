package com.school_of_company.signup.viewmodel.uistate

sealed interface SignUpUiState {
    object Success : SignUpUiState
    object Loading : SignUpUiState
    object NotSmsCheck : SignUpUiState
    object Conflict : SignUpUiState
    data class Error(val exception: Throwable) : SignUpUiState
}
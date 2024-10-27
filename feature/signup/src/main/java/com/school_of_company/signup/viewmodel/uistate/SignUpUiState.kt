package com.school_of_company.signup.viewmodel.uistate

sealed class SignUpUiState {
    object Success: SignUpUiState()
    object Loading: SignUpUiState()
    object PasswordMismatch: SignUpUiState()
    object PasswordValid: SignUpUiState()
    object DuplicateAccount: SignUpUiState()
    data class Error(val exception: Throwable): SignUpUiState()
}
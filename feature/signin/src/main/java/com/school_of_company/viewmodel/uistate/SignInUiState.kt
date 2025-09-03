package com.school_of_company.signin.viewmodel.uistate

sealed interface SignInUiState {
    object Loading : SignInUiState
    object Success : SignInUiState
    data class Error(
        val exception: Throwable? = null,
        val messageResId: Int,
        val errorType: ErrorType
    ) : SignInUiState

    enum class ErrorType {
        PASSWORD, NOT_FOUND, BAD_REQUEST, SERVER, GENERAL, FORBIDDEN
    }
}
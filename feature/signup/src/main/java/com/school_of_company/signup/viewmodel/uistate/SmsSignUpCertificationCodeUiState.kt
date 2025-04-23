package com.school_of_company.signup.viewmodel.uistate

sealed interface SmsSignUpCertificationCodeUiState {
    object Loading : SmsSignUpCertificationCodeUiState
    object Success : SmsSignUpCertificationCodeUiState
    data class Error(
        val exception: Throwable? = null,
        val messageResId: Int,
        val errorType: ErrorType
    ) : SmsSignUpCertificationCodeUiState

    enum class ErrorType {
        Unauthorized, BAD_REQUEST, GENERAL
    }
}
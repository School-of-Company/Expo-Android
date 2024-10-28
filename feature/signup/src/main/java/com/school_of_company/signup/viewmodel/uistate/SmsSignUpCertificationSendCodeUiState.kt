package com.school_of_company.signup.viewmodel.uistate

sealed interface SmsSignUpCertificationSendCodeUiState {
    object Success: SmsSignUpCertificationSendCodeUiState
    object Loading: SmsSignUpCertificationSendCodeUiState
    data class Error(val exception: Throwable): SmsSignUpCertificationSendCodeUiState
}
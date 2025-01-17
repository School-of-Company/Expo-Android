package com.school_of_company.sms.viewmodel.uiState

sealed class SendSmsUiState {
    object Loading : SendSmsUiState()
    object Success : SendSmsUiState()
    data class Error(val exception: Throwable) : SendSmsUiState()
}
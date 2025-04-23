package com.school_of_company.sms.viewmodel.uiState

sealed interface SendSmsUiState {
    object Loading : SendSmsUiState
    object Success : SendSmsUiState
    data class Error(val exception: Throwable) : SendSmsUiState
}
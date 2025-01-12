package com.school_of_company.user.viewmodel.uistate

sealed interface ServiceWithdrawalUiState {
    object Loading : ServiceWithdrawalUiState
    object Success : ServiceWithdrawalUiState
    data class Error(val exception: Throwable) : ServiceWithdrawalUiState
}
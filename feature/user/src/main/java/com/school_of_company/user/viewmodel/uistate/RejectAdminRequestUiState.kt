package com.school_of_company.user.viewmodel.uistate

sealed interface RejectAdminRequestUiState {
    object Loading : RejectAdminRequestUiState
    object Success : RejectAdminRequestUiState
    data class Error(val exception: Throwable) : RejectAdminRequestUiState
}
package com.school_of_company.user.viewmodel.uistate

sealed interface AllowAdminRequestUiState {
    object Loading : AllowAdminRequestUiState
    object Success : AllowAdminRequestUiState
    data class Error(val exception: Throwable) : AllowAdminRequestUiState
}
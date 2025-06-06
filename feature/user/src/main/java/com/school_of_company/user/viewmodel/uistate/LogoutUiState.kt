package com.school_of_company.user.viewmodel.uistate

sealed interface LogoutUiState {
    object Loading : LogoutUiState
    object Success : LogoutUiState
    data class Error(val exception: Throwable) : LogoutUiState
}
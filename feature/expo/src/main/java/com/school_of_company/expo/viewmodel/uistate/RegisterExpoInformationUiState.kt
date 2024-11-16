package com.school_of_company.expo.viewmodel.uistate

sealed interface RegisterExpoInformationUiState {
    object Loading : RegisterExpoInformationUiState
    object Success : RegisterExpoInformationUiState
    data class Error(val exception: Throwable) : RegisterExpoInformationUiState
}
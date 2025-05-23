package com.school_of_company.expo.viewmodel.uistate

sealed interface DeleteExpoInformationUiState {
    object Loading : DeleteExpoInformationUiState
    object Success : DeleteExpoInformationUiState
    data class Error(val exception: Throwable) : DeleteExpoInformationUiState
}
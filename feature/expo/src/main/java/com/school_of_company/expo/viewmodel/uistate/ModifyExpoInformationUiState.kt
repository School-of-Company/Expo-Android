package com.school_of_company.expo.viewmodel.uistate

sealed interface ModifyExpoInformationUiState {
    object Loading : ModifyExpoInformationUiState
    object Success : ModifyExpoInformationUiState
    data class Error(val exception: Throwable) : ModifyExpoInformationUiState
}
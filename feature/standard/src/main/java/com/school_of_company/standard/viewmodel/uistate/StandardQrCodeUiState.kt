package com.school_of_company.standard.viewmodel.uistate

sealed interface StandardQrCodeUiState {
    object Loading : StandardQrCodeUiState
    object Success : StandardQrCodeUiState
    data class Error(val exception: Throwable) : StandardQrCodeUiState
}
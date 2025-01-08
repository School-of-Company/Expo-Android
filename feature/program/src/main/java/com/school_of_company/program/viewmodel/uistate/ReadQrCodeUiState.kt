package com.school_of_company.program.viewmodel.uistate

sealed interface ReadQrCodeUiState {
    object Loading : ReadQrCodeUiState
    object Success: ReadQrCodeUiState
    data class Error(val exception: Throwable) : ReadQrCodeUiState
}
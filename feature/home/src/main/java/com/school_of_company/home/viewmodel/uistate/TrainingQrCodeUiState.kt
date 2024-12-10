package com.school_of_company.home.viewmodel.uistate

sealed interface TrainingQrCodeUiState {
    object Loading : TrainingQrCodeUiState
    object Success: TrainingQrCodeUiState
    data class Error(val exception: Throwable) : TrainingQrCodeUiState
}
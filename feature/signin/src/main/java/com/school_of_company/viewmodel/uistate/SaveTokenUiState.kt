package com.school_of_company.signin.viewmodel.uistate

sealed interface SaveTokenUiState {
    object Loading: SaveTokenUiState
    object Success: SaveTokenUiState
    data class Error(val exception: Throwable): SaveTokenUiState
}
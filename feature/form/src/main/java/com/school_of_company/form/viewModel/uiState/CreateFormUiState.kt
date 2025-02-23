package com.school_of_company.form.viewModel.uiState

internal sealed interface CreateFormUiState {
    object Loading : CreateFormUiState
    object Success : CreateFormUiState
    data class Error(val exception: Throwable) : CreateFormUiState
}
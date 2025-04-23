package com.school_of_company.form.viewModel.uiState

internal sealed interface GetFormUiState {
    object Loading : GetFormUiState
    object Success : GetFormUiState
    data class Error(val exception: Throwable) : GetFormUiState
}
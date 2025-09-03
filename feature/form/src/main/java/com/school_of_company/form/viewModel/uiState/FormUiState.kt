package com.school_of_company.form.viewModel.uiState

internal sealed interface FormUiState {
    object Loading : FormUiState
    object Success : FormUiState
    object NotFound : FormUiState
    object Conflict : FormUiState
    data class Error(val exception: Throwable) : FormUiState
}
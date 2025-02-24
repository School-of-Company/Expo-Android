package com.school_of_company.form.viewModel.uiState

import com.school_of_company.model.model.form.DynamicFormModel

internal sealed interface GetFormUiState {
    object Loading : GetFormUiState
    data class Success(val list: List<DynamicFormModel>) : GetFormUiState
    data class Error(val exception: Throwable) : GetFormUiState
}
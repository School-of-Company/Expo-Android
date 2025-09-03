package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.model.juso.JusoModel

sealed interface GetAddressUiState {
    object Loading : GetAddressUiState
    data class Success(val data: List<JusoModel>) : GetAddressUiState
    data class Error(val exception: Throwable) : GetAddressUiState
}
package com.school_of_company.home.viewmodel.uistate

import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel

sealed interface GetExpoInformationUiState {
    object Loading : GetExpoInformationUiState
    data class Success(val data: ExpoRequestAndResponseModel) : GetExpoInformationUiState
    data class Error(val exception: Throwable) : GetExpoInformationUiState
}
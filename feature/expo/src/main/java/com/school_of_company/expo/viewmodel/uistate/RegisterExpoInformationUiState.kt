package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.expo.ExpoIdResponseEntity

sealed interface RegisterExpoInformationUiState {
    object Loading : RegisterExpoInformationUiState
    data class Success(val data: ExpoIdResponseEntity): RegisterExpoInformationUiState
    data class Error(val exception: Throwable) : RegisterExpoInformationUiState
}
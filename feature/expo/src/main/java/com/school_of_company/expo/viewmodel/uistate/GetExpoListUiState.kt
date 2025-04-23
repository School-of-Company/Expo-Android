package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.expo.ExpoListResponseEntity

sealed interface GetExpoListUiState {
    object Loading : GetExpoListUiState
    object Empty : GetExpoListUiState
    data class Success(val data: List<ExpoListResponseEntity>) : GetExpoListUiState
    data class Error(val exception: Throwable) : GetExpoListUiState
}
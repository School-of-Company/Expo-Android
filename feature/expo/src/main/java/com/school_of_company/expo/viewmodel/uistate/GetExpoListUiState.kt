package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.ImmutableList

sealed interface GetExpoListUiState {
    object Loading : GetExpoListUiState
    object Empty : GetExpoListUiState
    data class Success(val data: ImmutableList<ExpoListResponseEntity>) : GetExpoListUiState
    data class Error(val exception: Throwable) : GetExpoListUiState
}
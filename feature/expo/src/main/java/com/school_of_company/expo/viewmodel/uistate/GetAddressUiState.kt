package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.model.juso.JusoModel
import kotlinx.collections.immutable.PersistentList

sealed interface GetAddressUiState {
    object Loading : GetAddressUiState
    data class Success(val data: PersistentList<JusoModel>) : GetAddressUiState
    data class Error(val exception: Throwable) : GetAddressUiState
}
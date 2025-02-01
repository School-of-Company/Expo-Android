package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.model.kakao.KakaoAddressModel

sealed interface GetCoordinatesUiState {
    object Loading : GetCoordinatesUiState
    data class Success(val data: KakaoAddressModel) : GetCoordinatesUiState
    data class Error(val exception: Throwable) : GetCoordinatesUiState
}
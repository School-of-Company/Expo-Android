package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.model.kakao.KakaoGeocodingModel

sealed interface GetCoordinatesToAddressUiState {
    object Loading : GetCoordinatesToAddressUiState
    data class Success(val data: KakaoGeocodingModel) : GetCoordinatesToAddressUiState
    data class Error(val exception: Throwable) : GetCoordinatesToAddressUiState
}
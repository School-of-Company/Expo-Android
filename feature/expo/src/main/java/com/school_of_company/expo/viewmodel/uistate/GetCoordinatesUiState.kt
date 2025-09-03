package com.school_of_company.expo.viewmodel.uistate

import com.school_of_company.model.model.kakao.KakaoGetCoordinatesModel

sealed interface GetCoordinatesUiState {
    object Loading : GetCoordinatesUiState
    data class Success(val data: KakaoGetCoordinatesModel) : GetCoordinatesUiState
    data class Error(val exception: Throwable) : GetCoordinatesUiState
}
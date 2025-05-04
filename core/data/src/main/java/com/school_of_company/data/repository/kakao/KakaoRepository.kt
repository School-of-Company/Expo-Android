package com.school_of_company.data.repository.kakao

import com.school_of_company.model.model.kakao.KakaoGeocodingModel
import com.school_of_company.model.model.kakao.KakaoGetCoordinatesModel
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun getCoordinates(address: String): Flow<KakaoGetCoordinatesModel>
    fun getAddress(x: String, y: String): Flow<KakaoGeocodingModel>
}
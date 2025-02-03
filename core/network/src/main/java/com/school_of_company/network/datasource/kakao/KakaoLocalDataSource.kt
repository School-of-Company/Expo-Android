package com.school_of_company.network.datasource.kakao

import com.school_of_company.network.dto.kakao.KakaoGeocodingResponse
import com.school_of_company.network.dto.kakao.KakaoGetCoordinatesResponse
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {
    fun getAddress(x: String, y: String): Flow<KakaoGeocodingResponse>
    fun getCoordinates(address: String, size: Int): Flow<KakaoGetCoordinatesResponse>
}
package com.school_of_company.network.datasource.kakao

import com.school_of_company.network.dto.kakao.KakaoGetAddressResponse
import com.school_of_company.network.dto.kakao.KakaoGetCoordinatesResponse
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {
    fun getAddress(x: String, y: String): Flow<KakaoGetAddressResponse>
    fun getCoordinates(address: String, size: Int): Flow<KakaoGetCoordinatesResponse>
}
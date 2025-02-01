package com.school_of_company.network.datasource.kakao

import com.school_of_company.network.dto.kakao.KakaoAddressResponse
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {
    fun getCoordinates(address: String, size: Int): Flow<KakaoAddressResponse>
}
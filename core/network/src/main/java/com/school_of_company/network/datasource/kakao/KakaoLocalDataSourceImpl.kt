package com.school_of_company.network.datasource.kakao

import com.school_of_company.network.api.KakaoAPI
import com.school_of_company.network.dto.kakao.KakaoGetAddressResponse
import com.school_of_company.network.dto.kakao.KakaoGetCoordinatesResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val service: KakaoAPI
) : KakaoLocalDataSource {
    override fun getCoordinates(address: String, size: Int): Flow<KakaoGetCoordinatesResponse> =
        performApiRequest {
            service.getCoordinates(
                address = address,
                size = size,
            )
        }

    override fun getAddress(x: String, y: String): Flow<KakaoGetAddressResponse> =
        performApiRequest {
            service.getAddress(
                x = x,
                y = y,
            )
        }
}

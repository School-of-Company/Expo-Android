package com.school_of_company.network.datasource.kakao

import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.KakaoLocalApi
import com.school_of_company.network.dto.kakao.KakaoAddressResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val kakaoLocalApi: KakaoLocalApi
) : KakaoLocalDataSource {
    override suspend fun getCoordinates(address: String, size: Int): Flow<KakaoAddressResponse> =
        performApiRequest {
            kakaoLocalApi.getCoordinates(
                apiKey = "KakaoAK ${BuildConfig.KAKAO_REST_KEY}",
                address = address,
                size = size,
            )
        }
}

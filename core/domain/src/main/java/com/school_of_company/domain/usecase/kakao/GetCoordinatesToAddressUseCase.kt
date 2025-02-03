package com.school_of_company.domain.usecase.kakao

import com.school_of_company.data.repository.kakao.KakaoRepository
import com.school_of_company.model.model.kakao.KakaoGeocodingModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoordinatesToAddressUseCase @Inject constructor(
    private val repository: KakaoRepository
) {
    operator fun invoke(x: String, y: String): Flow<KakaoGeocodingModel> =
        repository.getAddress(
            x = x,
            y = y
        )
}
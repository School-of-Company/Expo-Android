package com.school_of_company.domain.usecase.kakao

import com.school_of_company.data.repository.kakao.KakaoRepository
import com.school_of_company.model.model.kakao.KakaoAddressModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(
    private val repository: KakaoRepository
) {
    companion object {
        private const val DEFAULT_SIZE = 1
    }

    suspend operator fun invoke(address: String): Flow<KakaoAddressModel> =
        repository.getCoordinates(
            address = address,
            size = DEFAULT_SIZE
        )
}
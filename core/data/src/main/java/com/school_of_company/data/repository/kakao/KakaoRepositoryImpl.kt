package com.school_of_company.data.repository.kakao

import com.school_of_company.model.model.kakao.KakaoGetCoordinatesModel
import com.school_of_company.network.datasource.kakao.KakaoLocalDataSource
import com.school_of_company.network.mapper.kakao.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoLocalDataSource: KakaoLocalDataSource,
) : KakaoRepository {
    override fun getCoordinates(address: String, size: Int): Flow<KakaoGetCoordinatesModel> {
        return kakaoLocalDataSource.getCoordinates(
            address = address,
            size = size
        ).transform { it.toModel() }
    }
}

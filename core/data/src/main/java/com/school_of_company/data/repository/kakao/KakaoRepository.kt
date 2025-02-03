package com.school_of_company.data.repository.kakao

import com.school_of_company.model.model.kakao.KakaoGetCoordinatesModel
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun getCoordinates(address: String, size: Int): Flow<KakaoGetCoordinatesModel>
}
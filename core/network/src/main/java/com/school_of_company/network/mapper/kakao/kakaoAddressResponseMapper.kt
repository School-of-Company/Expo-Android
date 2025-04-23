package com.school_of_company.network.mapper.kakao

import com.school_of_company.model.model.kakao.KakaoGetCoordinatesModel
import com.school_of_company.network.dto.kakao.KakaoGetCoordinatesResponse

fun KakaoGetCoordinatesResponse.toModel(): KakaoGetCoordinatesModel {
    val document = documents.firstOrNull() ?: return KakaoGetCoordinatesModel(
        addressName = "Unknown",
        x = "0.0",
        y = "0.0"
    )

    return KakaoGetCoordinatesModel(
        addressName = document.addressName,
        x = document.x,
        y = document.y
    )
}

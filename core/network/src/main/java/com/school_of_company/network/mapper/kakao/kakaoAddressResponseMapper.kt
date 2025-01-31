package com.school_of_company.network.mapper.kakao

import com.school_of_company.model.model.kakao.KakaoAddressModel
import com.school_of_company.network.dto.kakao.KakaoAddressResponse

fun KakaoAddressResponse.toModel(): KakaoAddressModel {
    val document = documents.firstOrNull() ?: return KakaoAddressModel(
        addressName = "Unknown",
        x = "0.0",
        y = "0.0"
    )

    return KakaoAddressModel(
        addressName = document.addressName,
        x = document.x,
        y = document.y
    )
}

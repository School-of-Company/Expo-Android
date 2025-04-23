package com.school_of_company.network.mapper.kakao

import com.school_of_company.model.model.kakao.KakaoGeocodingModel
import com.school_of_company.network.dto.kakao.KakaoGetAddressResponse

fun KakaoGetAddressResponse.toModel(): KakaoGeocodingModel {
    val document = documents.firstOrNull() ?: return KakaoGeocodingModel(
        addressName = "Unknown",
    )

    return KakaoGeocodingModel(addressName = document.address?.addressName ?: "Unknown")
}

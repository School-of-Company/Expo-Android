package com.school_of_company.model.model.kakao

data class KakaoGetCoordinatesModel(
    val addressName: String,
    val x: String, // 경도
    val y: String, // 위도
)

package com.school_of_company.network.dto.kakao

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoAddressResponse(
    @Json(name = "meta") val meta: Meta,
    @Json(name = "documents") val documents: List<Document>,
)

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "pageable_count") val pageableCount: Int,
    @Json(name = "is_end") val isEnd: Boolean,
)

@JsonClass(generateAdapter = true)
data class Document(
    @Json(name = "address_name") val addressName: String,
    @Json(name = "x") val x: String, // 경도
    @Json(name = "y") val y: String, // 위도
)

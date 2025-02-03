package com.school_of_company.network.dto.kakao

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KakaoGeocodingResponse(
    @Json(name = "meta") val meta: GeocodingMeta,
    @Json(name = "documents") val documents: List<GeocodingDocument>
)

@JsonClass(generateAdapter = true)
data class GeocodingMeta(
    @Json(name = "total_count") val totalCount: Int
)

@JsonClass(generateAdapter = true)
data class GeocodingDocument(
    @Json(name = "address") val address: Address?,
    @Json(name = "road_address") val roadAddress: RoadAddress?
)

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name = "address_name") val addressName: String,
    @Json(name = "region_1depth_name") val region1depthName: String,
    @Json(name = "region_2depth_name") val region2depthName: String,
    @Json(name = "region_3depth_name") val region3depthName: String,
    @Json(name = "mountain_yn") val mountainYn: String,
    @Json(name = "main_address_no") val mainAddressNo: String,
    @Json(name = "sub_address_no") val subAddressNo: String
)

@JsonClass(generateAdapter = true)
data class RoadAddress(
    @Json(name = "address_name") val addressName: String,
    @Json(name = "region_1depth_name") val region1depthName: String,
    @Json(name = "region_2depth_name") val region2depthName: String,
    @Json(name = "region_3depth_name") val region3depthName: String,
    @Json(name = "road_name") val roadName: String,
    @Json(name = "underground_yn") val undergroundYn: String,
    @Json(name = "main_building_no") val mainBuildingNo: String,
    @Json(name = "sub_building_no") val subBuildingNo: String,
    @Json(name = "building_name") val buildingName: String?,
    @Json(name = "zone_no") val zoneNo: String
)
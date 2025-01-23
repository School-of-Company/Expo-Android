package com.school_of_company.network.dto.juso

data class AddressResponse(
    val results: Results
)

data class Results(
    val common: Common,
    val juso: List<Juso>?
)

data class Common(
    val totalCount: String,
    val currentPage: String,
    val countPerPage: String,
    val errorCode: String,
    val errorMessage: String
)

data class Juso(
    val roadAddr: String,
    val roadAddrPart1: String,
    val roadAddrPart2: String?,
    val jibunAddr: String,
    val engAddr: String,
    val zipNo: String,
    val admCd: String,
    val rnMgtSn: String,
    val bdMgtSn: String,
    val detBdNmList: String?,
    val bdNm: String?,
    val bdKdcd: String?,
    val siNm: String,
    val sggNm: String,
    val emdNm: String,
    val liNm: String?,
    val rn: String,
    val udrtYn: String?,
    val buldMnnm: String?,
    val buldSlno: String?,
    val mtYn: String?,
    val lnbrMnnm: String?,
    val lnbrSlno: String?,
    val emdNo: String?
)
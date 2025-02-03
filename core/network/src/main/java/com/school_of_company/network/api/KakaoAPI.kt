package com.school_of_company.network.api

import com.school_of_company.network.dto.kakao.KakaoAddressResponse
import com.school_of_company.network.dto.kakao.KakaoGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {

    @GET("search/address.json")
    suspend fun getCoordinates(
        @Header("Authorization") apiKey: String,
        @Header("Content-Type") contentType: String = "application/json;charset=UTF-8",
        @Query("query") address: String,
        @Query("size") size: Int,
    ): KakaoAddressResponse

    @GET("geo/coord2address.json")
    suspend fun getAddress(
        @Header("Authorization") apiKey: String,
        @Header("Content-Type") contentType: String = "application/json;charset=UTF-8",
        @Query("x") x: String,
        @Query("y") y: String,
    ): KakaoGeocodingResponse
}
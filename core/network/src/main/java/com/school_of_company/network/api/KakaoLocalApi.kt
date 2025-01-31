package com.school_of_company.network.api

import com.school_of_company.network.dto.kakao.KakaoAddressResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocalApi {

    @GET("search/address.json")
    suspend fun getCoordinates(
        @Header("Authorization") apiKey: String,
        @Query("query") address: String,
        @Header("Content-Type") contentType: String = "application/json;charset=UTF-8",
        @Query("size") size: Int,
    ): KakaoAddressResponse
}
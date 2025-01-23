package com.school_of_company.network.api

import com.school_of_company.network.dto.juso.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApi {
    @GET("addrlink/addrLinkApi.do")
    suspend fun getAddress(
        @Query("confmKey") confmKey: String,
        @Query("currentPage") currentPage: Int,
        @Query("countPerPage") countPerPage: Int,
        @Query("keyword") keyword: String,
        @Query("resultType") resultType: String,
    ): AddressResponse
}
package com.school_of_company.network.api

import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberSendRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SmsAPI {

    @POST("/sms")
    suspend fun smsSignUpCertificationNumberSend(
        @Body body: SmsSignUpCertificationNumberSendRequest
    )

    @GET("/sms")
    suspend fun smsSignUpCertificationNumberCertification(
        @Query("phoneNumber") phoneNumber: String,
        @Query("code") code: String
    )
}
package com.school_of_company.network.api

import com.school_of_company.network.dto.auth.request.AdminSignInRequest
import com.school_of_company.network.dto.auth.request.AdminSignUpRequest
import com.school_of_company.network.dto.auth.response.AdminTokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthAPI {

    @POST("/auth")
    suspend fun adminSignUp(
        @Body body: AdminSignUpRequest
    )

    @POST("/auth/signin")
    suspend fun adminSignIn(
        @Body body: AdminSignInRequest
    ) : AdminTokenResponse

    @PATCH("/auth")
    suspend fun adminTokenRefresh(
        @Header ("Authorization") refreshToken: String
    ) : AdminTokenResponse

    @DELETE("auth")
    suspend fun adminLogout()
}
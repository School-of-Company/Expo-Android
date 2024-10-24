package com.school_of_company.network.datasource.auth

import com.school_of_company.network.dto.auth.request.AdminSignInRequest
import com.school_of_company.network.dto.auth.request.AdminSignUpRequest
import com.school_of_company.network.dto.auth.response.AdminTokenResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun adminSignUp(body: AdminSignUpRequest) : Flow<Unit>
    fun adminSignIn(body: AdminSignInRequest) : Flow<AdminTokenResponse>
    fun adminTokenRefresh() : Flow<AdminTokenResponse>
    fun adminLogout() : Flow<Unit>
}
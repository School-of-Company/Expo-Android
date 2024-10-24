package com.school_of_company.network.datasource.auth

import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.dto.auth.request.AdminSignInRequest
import com.school_of_company.network.dto.auth.request.AdminSignUpRequest
import com.school_of_company.network.dto.auth.response.AdminTokenResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthAPI
) : AuthDataSource {
    override fun adminSignUp(body: AdminSignUpRequest): Flow<Unit> =
        performApiRequest { service.adminSignUp(body = body) }

    override fun adminSignIn(body: AdminSignInRequest): Flow<AdminTokenResponse> =
        performApiRequest { service.adminSignIn(body = body) }

    override fun adminTokenRefresh(refreshToken: String): Flow<AdminTokenResponse> =
        performApiRequest { service.adminTokenRefresh(refreshToken = refreshToken) }

    override fun adminLogout(): Flow<Unit> =
        performApiRequest { service.adminLogout() }

}
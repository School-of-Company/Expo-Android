package com.school_of_company.data.repository.auth

import com.school_of_company.model.model.auth.AdminTokenResponseModel
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun adminSignUp(body: AdminSignUpRequestParam) : Flow<Unit>
    fun adminSignIn(body: AdminSignInRequestParam) : Flow<AdminTokenResponseModel>
    fun adminTokenRefresh() : Flow<AdminTokenResponseModel>
    fun adminLogout() : Flow<Unit>
    fun getRefreshToken() : Flow<String>
    suspend fun saveToken(token: AdminTokenResponseModel)
    suspend fun deleteTokenData()
}
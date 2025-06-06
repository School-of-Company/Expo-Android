package com.school_of_company.data.repository.auth

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.model.model.auth.AdminTokenResponseModel
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.mapper.auth.request.toDto
import com.school_of_company.network.mapper.auth.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDatasource: AuthDataSource,
    private val localDataSource: AuthTokenDataSource
) : AuthRepository {
    override fun adminSignUp(body: AdminSignUpRequestParam): Flow<Unit> {
        return remoteDatasource.adminSignUp(body = body.toDto())
    }

    override fun adminSignIn(body: AdminSignInRequestParam): Flow<AdminTokenResponseModel> {
        return remoteDatasource.adminSignIn(
            body = body.toDto()
        ).transform { response ->
            emit(response.toModel())
        }
    }

    override fun adminTokenRefresh(): Flow<AdminTokenResponseModel> {
        return remoteDatasource.adminTokenRefresh().transform { response ->
            emit(response.toModel())
        }
    }

    override fun adminLogout(): Flow<Unit> {
        return remoteDatasource.adminLogout()
    }

    override fun getRefreshToken(): Flow<String> {
        return localDataSource.getRefreshToken()
    }

    override suspend fun saveToken(token: AdminTokenResponseModel) {
        localDataSource.setAccessToken(token.accessToken)
        localDataSource.setRefreshToken(token.refreshToken)
        localDataSource.setAccessTokenExp(token.accessTokenExpiresIn)
        localDataSource.setRefreshTokenExp(token.refreshTokenExpiresIn)
    }

    override suspend fun deleteTokenData() {
        localDataSource.removeAccessToken()
        localDataSource.removeRefreshToken()
        localDataSource.removeAccessTokenExp()
        localDataSource.removeRefreshTokenExp()
    }
}
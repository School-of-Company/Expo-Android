package com.school_of_company.network.util

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AuthAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

/**
 * TokenAuthenticator는 토큰이 만료될 경우 자동으로 새로운 토큰을 발급받고,
 * 해당 토큰으로 다시 요청을 시도하는 역할을 합니다.
 */
class TokenAuthenticator @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // DataSource에서 refreshToken을 가져옵니다.
        val refreshToken = runBlocking { authTokenDataSource.getRefreshToken().first() }

        // refreshToken을 활용해 새로운 accessToken을 발급받습니다.
        val newAccessToken = refreshAccessToken(refreshToken)

        // 새로 발급된 accessToken이 유효하다면 헤더에 추가하고, null이라면 인증 실패를 합니다.
        return if (newAccessToken.isNullOrEmpty()) {
            null
        } else {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }

    // refreshAcessToken은 refreshToken을 사용해 새로운 accessToken을 서버에서 받아옵니다.
    private fun refreshAccessToken(refreshToken: String) : String? {
        return try {
            // 서버에 요청을 보내는 AuthAPI 객체를 만듭니다.
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val authApi = retrofit.create(AuthAPI::class.java)
            val respone = runBlocking { authApi.adminTokenRefresh("Bearer $refreshToken") }

            // 새롭게 발급을 받은 토큰을 DataSource에 저장하고, 이후 요청에 사용하도록 합니다.
            runBlocking {
                with(authTokenDataSource) {
                    setAccessToken(respone.accessToken)
                    setAccessTokenExp(respone.accessTokenExpiresIn)
                    setRefreshToken(respone.refreshToken)
                    setRefreshTokenExp(respone.refreshTokenExpiresIn)
                }
            }

            respone.accessToken
        } catch (e: Exception) {
            null
        }
    }
}
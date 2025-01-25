package com.school_of_company.network.util

import android.util.Log
import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AuthAPI
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

/**
 * TokenAuthenticator는 서버에서 받은 액세스 토큰이 만료되었을 때,
 * 자동으로 새로운 액세스 토큰을 갱신하고, 갱신된 토큰으로 요청을 다시 시도하는 역할을 합니다.
 * 이 클래스는 OkHttp의 Authenticator 인터페이스를 구현하여, 인증이 필요한 요청에 대해 자동으로 토큰을 갱신합니다.
 */
class TokenAuthenticator @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource,
) : Authenticator {

    /**
     * 인증 헤더에 액세스 토큰을 추가하기 위해, 만약 토큰이 만료되었으면 새로 갱신된 토큰을 사용하여
     * 요청을 새로 빌드합니다. 만약 토큰 갱신에 실패했다면 null을 반환하여 요청을 보내지 않습니다.
     *
     * @param route 현재 요청의 라우트 정보
     * @param response 서버의 응답 객체 (401 오류가 발생하면 호출)
     * @return 새롭게 갱신된 액세스 토큰을 포함한 요청을 반환하거나, 토큰 갱신 실패 시 null을 반환
     */
    override fun authenticate(route: Route?, response: Response): Request? {
        // 액세스 토큰을 갱신합니다.
        val newToken = refreshAccessToken()

        // 토큰 갱신 실패 시 null을 반환하여 요청을 보내지 않습니다.
        if (newToken.isNullOrEmpty()) {
            return null
        }

        // 새로 갱신된 토큰을 Authorization 헤더에 추가하여 요청을 반환합니다.
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }

    /**
     * 서버에서 refreshToken을 사용하여 새로운 액세스 토큰을 받아오는 함수입니다.
     * 만약 토큰 갱신에 실패하면 null을 반환합니다.
     *
     * @return 갱신된 액세스 토큰 또는 실패 시 null
     */
    private fun refreshAccessToken(): String? {
        return try {
            // Retrofit을 사용하여 서버에 요청을 보냅니다.
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            // AuthAPI 인터페이스 생성
            val authApi = retrofit.create(AuthAPI::class.java)
            // 액세스 토큰 갱신 API 호출
            val response = runBlocking { authApi.adminTokenRefresh() }

            // 갱신된 토큰 정보를 DataSource에 저장
            runBlocking {
                with(authTokenDataSource) {
                    setAccessToken(response.accessToken)
                    setAccessTokenExp(response.accessTokenExpiresIn)
                    setRefreshToken(response.refreshToken)
                    setRefreshTokenExp(response.refreshTokenExpiresIn)
                }
            }

            // 새로운 액세스 토큰을 반환
            response.accessToken
        } catch (e: Exception) {
            // 토큰 갱신 실패 시 로그 출력
            Log.e("TokenAuthenticator", "Failed to refresh access token")
            null
        }
    }
}

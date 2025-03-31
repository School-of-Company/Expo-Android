package com.school_of_company.network.util

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * AuthInterceptor는 HTTP 요청마다 엑세스 토큰을 헤더에 추가하는 역할을 합니다.
 */
class AuthInterceptor @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource
) : Interceptor {
    private companion object {
        const val POST = "POST"
        const val GET = "GET"
        const val PATCH = "PATCH"
    }

    // 각 요청을 가로채고 토큰을 추가하여 서버로 전송합니다.
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val method = request.method

        // accessToken, refreshToken를 가져옵니다.
        val accessToken = runBlocking { authTokenDataSource.getAccessToken().first() }
        val refreshToken = runBlocking { authTokenDataSource.getRefreshToken().first() }

        // 새로운 요청을 생성하고, 특정 조건에 따라 헤더에 토큰을 추가합니다.
        val newRequest = when {
            // 인증이 필요없는 경로에 대해서는 토큰을 추가하지 않습니다.
            path.contains("/auth") && method in listOf(POST) -> {
                request
            }

            // kakao api 에 대해서는 KakaoRestApiKey 를 추가합니다
            Regex("/(search|geo)").containsMatchIn(path) && method in listOf(GET) -> {
                request
            }

            // "/sms"의 경로에 대해서는 토큰을 추가하지 않습니다.
            // "/sms/message"와 같은 경로에 대해서는 토큰을 추가할 수 있습니다.
            path == "/sms" && method in listOf(POST, GET) -> {
                request
            }

            // Juso API 요청을 그대로 반환 합니다.
            path.contains("addrlink/addrLinkApi.do") -> {
                request
            }

            // 특정 경로와 DELETE 메서드 요청에는 리프레시 토큰을 추가합니다.
            path.endsWith("/auth") && method == PATCH -> {
                request.newBuilder().addHeader("RefreshToken", "Bearer $refreshToken").build()
            }

            // 나머지의 경우에는 전부 acessToken을 추가합니다.
            else -> {
                request.newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
            }
        }

        // 수정된 요청을 사용해 서버에 요청을 보냅니다.
        val response = chain.proceed(newRequest)

        // 201과 204인 경우 200으로 응답을 변환합니다.
        return when (response.code) {
            201, 204 -> response.newBuilder().code(200).build()
            else -> response
        }
    }
}
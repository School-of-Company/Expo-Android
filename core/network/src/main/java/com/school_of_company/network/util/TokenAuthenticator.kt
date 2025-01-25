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
 * TokenAuthenticator는 토큰이 만료될 경우 자동으로 새로운 토큰을 발급받고,
 * 해당 토큰으로 다시 요청을 시도하는 역할을 합니다.
 */
class TokenAuthenticator @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val newToken = refreshAccessToken()

        if (newToken.isNullOrEmpty()) {
            return null
        }
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }


    // refreshAcessToken은 refreshToken을 사용해 새로운 accessToken을 서버에서 받아옵니다.
    private fun refreshAccessToken(): String? {
        return try {
            // 서버에 요청을 보내는 AuthAPI 객체를 만듭니다.
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val authApi = retrofit.create(AuthAPI::class.java)
            val response = runBlocking { authApi.adminTokenRefresh() }

            // 새롭게 발급을 받은 토큰을 DataSource에 저장하고, 이후 요청에 사용하도록 합니다.
            runBlocking {
                with(authTokenDataSource) {
                    setAccessToken(response.accessToken)
                    setAccessTokenExp(response.accessTokenExpiresIn)
                    setRefreshToken(response.refreshToken)
                    setRefreshTokenExp(response.refreshTokenExpiresIn)
                }
            }

            response.accessToken
        } catch (e: Exception) {
            Log.e("TokenAuthenticator", "Failed to refresh access token")
            null
        }
    }
}
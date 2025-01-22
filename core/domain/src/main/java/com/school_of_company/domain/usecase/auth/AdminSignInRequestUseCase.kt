package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import javax.inject.Inject

class AdminSignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(body: AdminSignInRequestParam) = runCatching {
        // 로그인 처리
        authRepository.adminSignIn(body).collect { token ->
            authRepository.saveToken(token) // 로그인 후 토큰 저장
        }
    }
}
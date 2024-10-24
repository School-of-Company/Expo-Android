package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.param.auth.AdminSignInRequestParam
import javax.inject.Inject

class AdminSignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: AdminSignInRequestParam) = runCatching {
        authRepository.adminSignIn(body = body)
    }
}
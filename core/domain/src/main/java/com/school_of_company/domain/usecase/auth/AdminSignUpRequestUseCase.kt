package com.school_of_company.domain.usecase.auth

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.model.param.auth.AdminSignUpRequestParam
import javax.inject.Inject

class AdminSignUpRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body:AdminSignUpRequestParam) = runCatching {
        authRepository.adminSignUp(body = body)
    }
}
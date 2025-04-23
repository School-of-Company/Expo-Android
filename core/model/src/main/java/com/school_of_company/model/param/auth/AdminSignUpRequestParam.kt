package com.school_of_company.model.param.auth

data class AdminSignUpRequestParam(
    val name: String,
    val nickname: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
)

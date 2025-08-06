package com.school_of_company.model.entity.admin

import androidx.compose.runtime.Immutable

@Immutable
data class AdminRequestAllowListResponseEntity(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val phoneNumber: String,
)

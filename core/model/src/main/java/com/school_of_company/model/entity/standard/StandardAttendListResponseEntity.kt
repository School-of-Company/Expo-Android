package com.school_of_company.model.entity.standard

import androidx.compose.runtime.Immutable

@Immutable
data class StandardAttendListResponseEntity(
    val id: Long,
    val name: String,
    val programName: String,
    val status: Boolean,
    val entryTime: String?,
    val leaveTime: String?
)

package com.school_of_company.model.entity.participant

import androidx.compose.runtime.Immutable

@Immutable
data class ParticipantInformationResponseEntity(
    val info: PageInfoEntity,
    val participant: List<ParticipantEntity>
)

@Immutable
data class PageInfoEntity(
    val totalPage: Int,
    val totalElement: Int
)

@Immutable
data class ParticipantEntity(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val informationStatus: Boolean
)

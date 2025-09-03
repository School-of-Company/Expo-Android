package com.school_of_company.model.entity.participant

data class ParticipantInformationResponseEntity(
    val info: PageInfoEntity,
    val participant: List<ParticipantEntity>
)

data class PageInfoEntity(
    val totalPage: Int,
    val totalElement: Int
)

data class ParticipantEntity(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val informationStatus: Boolean
)

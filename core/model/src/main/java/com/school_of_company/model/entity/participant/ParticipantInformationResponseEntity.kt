package com.school_of_company.model.entity.participant

data class ParticipantInformationResponseEntity(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val informationStatus: Boolean,
)
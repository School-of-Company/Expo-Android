package com.school_of_company.network.mapper.participant.response

import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse

fun ParticipantInformationResponse.toEntity(): ParticipantInformationResponseEntity =
    ParticipantInformationResponseEntity(
        id = this.id,
        name = this.name,
        phoneNumber = this.phoneNumber,
        informationStatus = this.informationStatus
    )
package com.school_of_company.network.mapper.participant.response

import com.school_of_company.model.entity.participant.PageInfoEntity
import com.school_of_company.model.entity.participant.ParticipantEntity
import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import com.school_of_company.network.dto.participant.response.PageInfo
import com.school_of_company.network.dto.participant.response.ParticipantInformationResponse
import com.school_of_company.network.dto.participant.response.ParticipantResponse

fun ParticipantInformationResponse.toEntity(): ParticipantInformationResponseEntity =
    ParticipantInformationResponseEntity(
        info = this.info.toEntity(),
        participant = this.participants.map { it.toEntity() }
    )

fun PageInfo.toEntity(): PageInfoEntity =
    PageInfoEntity(
        totalPage = this.totalPage,
        totalElement = this.totalElement
    )

fun ParticipantResponse.toEntity(): ParticipantEntity =
    ParticipantEntity(
        id = this.id,
        name = this.name,
        phoneNumber = this.phoneNumber,
        informationStatus = this.informationStatus
    )
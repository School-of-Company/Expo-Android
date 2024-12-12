package com.school_of_company.network.mapper.standard.response

import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import com.school_of_company.network.dto.standard.response.StandardAttendListResponse

fun StandardAttendListResponse.toModel(): StandardAttendListResponseEntity =
    StandardAttendListResponseEntity(
        id = id,
        name = this.name,
        affiliation = this.affiliation,
        position = this.position,
        programName = this.programName,
        status = this.status,
        entryTime = this.entryTime,
        leaveTime = this.leaveTime
    )
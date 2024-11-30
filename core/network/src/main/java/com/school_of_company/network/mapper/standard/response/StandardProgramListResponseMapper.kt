package com.school_of_company.network.mapper.standard.response

import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import com.school_of_company.network.dto.standard.response.StandardProgramListResponse

fun StandardProgramListResponse.toModel(): StandardProgramListResponseEntity =
    StandardProgramListResponseEntity(
        id = this.id,
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt,
    )

package com.school_of_company.network.mapper.standard.request

import com.school_of_company.model.model.standard.StandardRequestModel
import com.school_of_company.network.dto.standard.request.StandardRequest

fun StandardRequestModel.toDto(): StandardRequest =
    StandardRequest(
        title = this.title,
        startedAt = this.startedAt,
        endedAt = this.endedAt,
    )
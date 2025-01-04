package com.school_of_company.network.mapper.attendance.request

import com.school_of_company.model.param.attendance.StandardQrCodeRequestParam
import com.school_of_company.network.dto.attendance.request.StandardQrCodeRequest

fun StandardQrCodeRequestParam.toDto(): StandardQrCodeRequest =
    StandardQrCodeRequest(participantId = participantId, phoneNumber = phoneNumber)
package com.school_of_company.network.mapper.attendance.request

import com.school_of_company.model.param.attendance.TrainingQrCodeRequestParam
import com.school_of_company.network.dto.attendance.request.TrainingQrCodeRequest

fun TrainingQrCodeRequestParam.toDto(): TrainingQrCodeRequest =
    TrainingQrCodeRequest(traineeId = traineeId)
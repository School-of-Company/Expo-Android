package com.school_of_company.network.mapper.sms.request

import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import com.school_of_company.network.dto.sms.request.SendSmsToParticipantTraineeResponse

fun SendSmsToParticipantTraineeParam.toDto() =
    SendSmsToParticipantTraineeResponse(title, content, authority.name)

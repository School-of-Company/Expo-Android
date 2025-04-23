package com.school_of_company.model.param.sms

import com.school_of_company.model.enum.Authority

data class SendSmsToParticipantTraineeParam(
    val title: String,
    val content: String,
    val authority: Authority,
)
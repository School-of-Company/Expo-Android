package com.school_of_company.network.mapper.sms.request

import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberSendRequest

fun SmsSignUpCertificationNumberSendRequestParam.toDto(): SmsSignUpCertificationNumberSendRequest =
    SmsSignUpCertificationNumberSendRequest(
        phoneNumber = this.phoneNumber
    )
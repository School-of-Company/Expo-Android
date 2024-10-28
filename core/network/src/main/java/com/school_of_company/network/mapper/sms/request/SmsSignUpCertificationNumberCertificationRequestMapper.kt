package com.school_of_company.network.mapper.sms.request

import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberCertificationRequestParam
import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberCertificationRequest

fun SmsSignUpCertificationNumberCertificationRequestParam.toDto(): SmsSignUpCertificationNumberCertificationRequest =
    SmsSignUpCertificationNumberCertificationRequest(
        phoneNumber = this.phoneNumber,
        randomCode = this.randomCode
    )
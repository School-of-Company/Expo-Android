package com.school_of_company.data.repository.sms

import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import kotlinx.coroutines.flow.Flow

interface SmsRepository {
    fun smsSignUpCertificationNumberSend(body: SmsSignUpCertificationNumberSendRequestParam) : Flow<Unit>
    fun smsSignUpCertificationNumberCertification(phoneNumber: String, code: String) : Flow<Unit>
}
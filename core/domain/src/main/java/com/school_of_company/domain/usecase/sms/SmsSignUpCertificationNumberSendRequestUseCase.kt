package com.school_of_company.domain.usecase.sms

import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import javax.inject.Inject

class SmsSignUpCertificationNumberSendRequestUseCase @Inject constructor(
    private val repository: SmsRepository
) {
    operator fun invoke(body: SmsSignUpCertificationNumberSendRequestParam) = runCatching {
        repository.smsSignUpCertificationNumberSend(body = body)
    }
}
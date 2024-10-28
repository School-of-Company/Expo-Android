package com.school_of_company.domain.usecase.sms

import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberCertificationRequestParam
import javax.inject.Inject

class SmsSignUpCertificationNumberCertificationRequestUseCase @Inject constructor(
    private val repository: SmsRepository
) {
    operator fun invoke(body: SmsSignUpCertificationNumberCertificationRequestParam) = runCatching {
        repository.smsSignUpCertificationNumberCertification(body = body)
    }
}
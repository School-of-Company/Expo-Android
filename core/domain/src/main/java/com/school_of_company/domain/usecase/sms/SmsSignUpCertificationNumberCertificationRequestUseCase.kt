package com.school_of_company.domain.usecase.sms

import android.util.Log
import com.school_of_company.data.repository.sms.SmsRepository
import javax.inject.Inject

class SmsSignUpCertificationNumberCertificationRequestUseCase @Inject constructor(
    private val repository: SmsRepository
) {
    operator fun invoke(phoneNumber: String, code: String) = runCatching {
        repository.smsSignUpCertificationNumberCertification(
            phoneNumber = phoneNumber,
            code = code
        )
    }
}
package com.school_of_company.network.datasource.sms

import com.school_of_company.network.api.SmsAPI
import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberCertificationRequest
import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberSendRequest
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmsDataSourceImpl @Inject constructor(
    private val service: SmsAPI
) : SmsDataSource {
    override fun smsSignUpCertificationNumberSend(body: SmsSignUpCertificationNumberSendRequest): Flow<Unit> =
        performApiRequest { service.smsSignUpCertificationNumberSend(body = body) }

    override fun smsSignUpCertificationNumberCertification(body: SmsSignUpCertificationNumberCertificationRequest): Flow<Unit> =
        performApiRequest { service.smsSignUpCertificationNumberCertification(body = body) }
}
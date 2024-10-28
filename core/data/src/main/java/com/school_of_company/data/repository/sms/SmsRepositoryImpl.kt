package com.school_of_company.data.repository.sms

import com.school_of_company.model.param.sms.SmsSignUpCertificationNumberSendRequestParam
import com.school_of_company.network.datasource.sms.SmsDataSource
import com.school_of_company.network.mapper.sms.request.toDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val dataSource: SmsDataSource
) : SmsRepository {
    override fun smsSignUpCertificationNumberSend(body: SmsSignUpCertificationNumberSendRequestParam): Flow<Unit> {
        return dataSource.smsSignUpCertificationNumberSend(body = body.toDto())
    }

    override fun smsSignUpCertificationNumberCertification(phoneNumber: String, code: String): Flow<Unit> {
        return dataSource.smsSignUpCertificationNumberCertification(
            phoneNumber = phoneNumber,
            code = code
        )
    }
}
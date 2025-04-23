package com.school_of_company.network.datasource.sms

import com.school_of_company.network.dto.sms.request.SmsSignUpCertificationNumberSendRequest
import com.school_of_company.network.dto.sms.request.SendSmsToParticipantTraineeResponse
import kotlinx.coroutines.flow.Flow

interface SmsDataSource {
    fun smsSignUpCertificationNumberSend(body: SmsSignUpCertificationNumberSendRequest) : Flow<Unit>
    fun sendSmsToParticipantTrainee(id:String, body: SendSmsToParticipantTraineeResponse) : Flow<Unit>
    fun smsSignUpCertificationNumberCertification(phoneNumber: String, code: String) : Flow<Unit>
}
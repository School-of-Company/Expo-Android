package com.school_of_company.domain.usecase.sms

import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.model.param.sms.SendSmsToParticipantTraineeParam
import javax.inject.Inject

class SendSmsToParticipantTraineeUseCase @Inject constructor(
    private val repository: SmsRepository
) {
    operator fun invoke(id: String, body: SendSmsToParticipantTraineeParam) = runCatching {
        repository.sendSmsToParticipantTrainee(id = id, body = body)
    }
}
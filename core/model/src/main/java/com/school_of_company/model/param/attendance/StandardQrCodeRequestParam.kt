package com.school_of_company.model.param.attendance

data class StandardQrCodeRequestParam(
    val participantId: Long,
    val phoneNumber: String,
)

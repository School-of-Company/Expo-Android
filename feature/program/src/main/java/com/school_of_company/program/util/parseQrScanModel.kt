package com.school_of_company.program.util

import org.json.JSONObject

data class QrScanModel(
    val participantId: Long,
    val phoneNumber: String
)

internal fun String.parseStandardQrScanModel(): QrScanModel {
    val jsonObject = JSONObject(this)
    val participantId = jsonObject.optLong("participantId")
    val phoneNumber = jsonObject.optString("phoneNumber")

    return QrScanModel(participantId, phoneNumber)
}
internal fun String.parseTrainingQr(): String = JSONObject(this).optString("traineeId")


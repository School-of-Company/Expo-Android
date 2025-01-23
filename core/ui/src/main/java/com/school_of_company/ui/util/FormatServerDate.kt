package com.school_of_company.ui.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * 서버에서 받은 날짜 문자열을 지정된 형식으로 변환합니다.
 *
 * 입력 값에 따라 다음과 같이 동작합니다:
 * 1. "yyyy-MM-ddTHH:mm" 형식의 문자열은 "yyyyMMddHHmm" 형식으로 변환됩니다.
 * 2. "yyyy-MM-dd" 형식의 문자열은 "yyyyMMdd" 형식으로 변환됩니다.
 *
 * @receiver 서버에서 받은 날짜 문자열
 * @return 변환된 날짜 문자열 ("yyyyMMddHHmm" 또는 "yyyyMMdd" 형식)
 * @throws IllegalArgumentException 입력값이 null이거나 빈 문자열인 경우
 * @throws DateTimeParseException 날짜 형식이 지정된 포맷과 맞지 않는 경우
 */
fun String.formatServerDate(): String =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일",Locale.getDefault()))

fun String.formatNoneHyphenServerDate(): String =
    if (this.contains("T")) {
        // 날짜와 시간이 포함된 경우
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.getDefault()))
            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm", Locale.getDefault()))
    } else {
        // 날짜만 포함된 경우
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
            .format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault()))
    }

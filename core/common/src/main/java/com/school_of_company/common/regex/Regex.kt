package com.school_of_company.common.regex

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.checkEmailRegex() =
    this.matches(".*@.*".toRegex())

fun String.checkPasswordRegex() =
    this.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d\$!%*?&]{8,24}$".toRegex())

fun String.isValidDate(): Boolean =
    // 먼저, 문자열이 "yyyy-MM-dd" 형식에 맞는지 정규식을 사용하여 확인
    this.matches(Regex("\\d{4}-\\d{2}-\\d{2}")) &&

            // 형식이 맞다면 날짜가 실제로 유효한지 확인
            runCatching {
                // 입력된 문자열을 LocalDate로 파싱하여 날짜가 유효한지 확인
                LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
            }.isSuccess // 파싱이 성공하면 유효한 날짜, 실패하면 유효하지 않음

fun String.isValidDateTime(): Boolean =
    this.matches(Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) && runCatching {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }.isSuccess
    // 먼저, 문자열이 "yyyy-MM-dd HH:mm" 형식에 맞는지 확인
            // 예외가 발생하지 않으면 유효한 날짜와 시간을 확인
                // 날짜와 시간을 파싱하여 LocalDateTime 객체로 변환
                // 날짜 부분을 잘라서 실제 날짜로 변환하고, 해당 날짜가 유효한지 확인
                // 파싱된 날짜가 실제로 존재하는 날짜와 일치하는지 확인
                // 예외가 발생하면 유효하지 않은 값으로 간주하고 false 반환
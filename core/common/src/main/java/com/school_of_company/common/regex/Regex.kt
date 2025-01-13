package com.school_of_company.common.regex

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.checkEmailRegex() =
    this.matches(".*@.*".toRegex())

fun String.checkPasswordRegex() =
    this.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d\$!%*?&]{8,24}$".toRegex())


fun String.isValidDate(): Boolean =
    this.matches(Regex("\\d{4}-\\d{2}-\\d{2}")) && runCatching {
        LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
    }.isSuccess

fun String.isValidDateTime(): Boolean =
    this.matches(Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) && runCatching {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }.isSuccess
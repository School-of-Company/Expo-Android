package com.school_of_company.ui.util

import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
* 서버에서 받은 날짜 문자열을 한국어 형식으로 변환합니다.
*
* @return "MM월 dd일" 형식의 문자열
* @throws IllegalArgumentException 입력값이 null이거나 빈 문자열인 경우
* @throws DateTimeParseException 날짜 형식이 "MM-dd"가 아닌 경우
*/

fun String.formatServerDate(): String =
    MonthDay.parse(this, DateTimeFormatter.ofPattern("MM-dd"))
        .format(DateTimeFormatter.ofPattern("MM월 dd일", Locale.getDefault()))
package com.school_of_company.ui.util

import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatServerDate(): String =
    MonthDay.parse(this, DateTimeFormatter.ofPattern("MM-dd"))
        .format(DateTimeFormatter.ofPattern("MM월 dd일", Locale.getDefault()))
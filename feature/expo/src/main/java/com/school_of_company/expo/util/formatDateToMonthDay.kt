package com.school_of_company.expo.util

fun formatDateToMonthDay(date: String): String {
    return date.replace(Regex("^\\d{4}-(\\d{2})-(\\d{2})$"), "$1.$2")
}

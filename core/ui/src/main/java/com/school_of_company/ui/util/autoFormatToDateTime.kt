package com.school_of_company.ui.util

fun String.autoFormatToDateTime(): String {
    val year = this.take(4)
    val month = this.drop(4).take(2)
    val day = this.drop(6).take(2)
    val hour = this.drop(8).take(2)
    val minute = this.drop(10).take(2)

    return buildString {
        if (year.isNotEmpty()) append(year)
        if (month.isNotEmpty()) append("-$month")
        if (day.isNotEmpty()) append("-$day")
        if (hour.isNotEmpty()) append(" $hour")
        if (minute.isNotEmpty()) append(":$minute")
    }
}

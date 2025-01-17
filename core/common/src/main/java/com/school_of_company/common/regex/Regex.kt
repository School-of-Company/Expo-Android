package com.school_of_company.common.regex

fun String.checkEmailRegex() =
    this.matches(".*@.*".toRegex())

fun String.checkPasswordRegex() =
    this.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d\$!%*?&]{8,24}$".toRegex())


fun String.isValidDate(): Boolean {
    if (this.length != 8 || !this.matches(Regex("\\d{8}"))) return false
    val year = this.substring(0, 4).toInt()
    val month = this.substring(4, 6).toInt()
    val day = this.substring(6, 8).toInt()

    if (month !in 1..12 || day !in 1..31) return false

    val daysInMonth = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> return false
    }

    return day <= daysInMonth
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun String.isValidDateTime(): Boolean =
    this.matches(Regex("\\d{12}")) &&
            run {
                // 날짜와 시간의 유효성을 각각 확인
                val datePart = this.substring(0, 8)
                val timePart = this.substring(8)

                if (!datePart.isValidDate()) {
                    return@run false // 날짜가 유효하지 않으면 false 반환
                }

                // 시간 검증
                val hour = timePart.substring(0, 2).toInt() // 시간 (HH)
                val minute = timePart.substring(2, 4).toInt() // 분 (mm)

                // 시간이 유효한 범위(0~23시, 0~59분)에 있는지 확인
                hour in 0..23 && minute in 0..59
            }

fun String.isValidDateSequence(laterTime: String): Boolean =
    this.matches(Regex("\\d{8}"))
            && laterTime.matches(Regex("\\d{8}"))
            && this.isValidDate()
            && laterTime.isValidDate()
            && this <= laterTime
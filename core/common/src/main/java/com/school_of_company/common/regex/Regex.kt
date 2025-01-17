package com.school_of_company.common.regex

/**
 * 이메일 형식이 유효한지 확인하는 함수.
 *
 * 이 함수는 문자열이 기본적인 이메일 형식 (예: ex@ex.com)과 일치하는지 확인합니다.
 * 이메일 형식에 대한 검증은 ".*@.*" 정규식을 사용하여 수행됩니다.
 *
 * @return 이메일 형식이 맞으면 `true`, 아니면 `false`.
 */
fun String.checkEmailRegex(): Boolean =
    this.matches(".*@.*".toRegex())

/**
 * 비밀번호 형식이 유효한지 확인하는 함수.
 *
 * 비밀번호는 다음 조건을 만족해야 합니다:
 * - 최소 8자 이상, 최대 24자 이하
 * - 적어도 하나의 알파벳, 하나의 숫자, 하나의 특수문자(@$!%*?&) 포함
 *
 * @return 비밀번호 형식이 맞으면 `true`, 아니면 `false`.
 */
fun String.checkPasswordRegex(): Boolean =
    this.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d\$!%*?&]{8,24}$".toRegex())

/**
 * 문자열이 유효한 날짜 형식 (YYYYMMDD)인지 확인하는 함수.
 *
 * 이 함수는 날짜가 8자리로 되어 있는지 확인하고, 각 월과 일의 범위가 유효한지 확인합니다.
 * 또한, 윤년 여부를 고려하여 2월의 일수를 검사합니다.
 *
 * @return 날짜 형식이 유효하면 `true`, 아니면 `false`.
 */
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

/**
 * 주어진 연도가 윤년인지 확인하는 함수.
 *
 * 윤년은 다음 조건을 만족해야 합니다:
 * - 4로 나누어지지만 100으로 나누어지지 않거나,
 * - 400으로 나누어지는 연도
 *
 * @param year 확인할 연도
 * @return 윤년이면 `true`, 아니면 `false`.
 */
private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

/**
 * 문자열이 유효한 날짜-시간 형식 (YYYYMMDDHHMM)인지 확인하는 함수.
 *
 * 날짜 부분과 시간 부분을 분리하여 각각의 유효성을 검사합니다.
 * - 날짜 부분은 `isValidDate()` 함수를 통해 검증합니다.
 * - 시간 부분은 0시부터 23시까지의 시간과 0분부터 59분까지의 분을 유효한 범위로 확인합니다.
 *
 * @return 날짜와 시간이 유효하면 `true`, 아니면 `false`.
 */
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

/**
 * 두 날짜가 올바른 날짜 순서인지 확인하는 함수.
 *
 * 이 함수는 첫 번째 날짜와 두 번째 날짜가 모두 유효한 날짜 형식인지 확인하고,
 * 두 날짜가 동일하거나 첫 번째 날짜가 두 번째 날짜보다 이전인지를 비교합니다.
 *
 * @param laterTime 비교할 두 번째 날짜
 * @return 날짜 순서가 유효하면 `true`, 아니면 `false`.
 */
fun String.isValidDateSequence(laterTime: String): Boolean =
    this.matches(Regex("\\d{8}"))
            && laterTime.matches(Regex("\\d{8}"))
            && this.isValidDate()
            && laterTime.isValidDate()
            && this <= laterTime

/**
 * 두 날짜-시간이 올바른 날짜-시간 순서인지 확인하는 함수.
 *
 * 이 함수는 첫 번째 날짜-시간과 두 번째 날짜-시간이 모두 유효한 날짜-시간 형식인지 확인하고,
 * 두 날짜-시간이 동일하거나 첫 번째 날짜-시간이 두 번째 날짜-시간보다 이전인지를 비교합니다.
 *
 * @param laterTime 비교할 두 번째 날짜-시간
 * @return 날짜-시간 순서가 유효하면 `true`, 아니면 `false`.
 */
fun String.isValidDateTimeSequence(laterTime: String): Boolean =
    this.matches(Regex("\\d{12}"))
            && laterTime.matches(Regex("\\d{12}"))
            && this.isValidDateTime()
            && laterTime.isValidDateTime()
            && this <= laterTime

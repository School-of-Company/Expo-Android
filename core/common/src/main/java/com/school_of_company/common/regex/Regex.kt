package com.school_of_company.common.regex

fun String.checkEmailRegex() =
    this.matches(".*@.*".toRegex())

fun String.checkPasswordRegex() =
    this.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d\$!%*?&]{8,24}$".toRegex())
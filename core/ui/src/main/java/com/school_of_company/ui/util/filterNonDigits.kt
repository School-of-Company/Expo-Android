package com.school_of_company.ui.util

fun String.filterNonDigits() = this.filter { it.isDigit() }
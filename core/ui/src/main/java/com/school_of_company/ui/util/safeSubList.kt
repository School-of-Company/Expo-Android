package com.school_of_company.ui.util

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> {
    val start = fromIndex.coerceIn(0, size)
    val end = toIndex.coerceIn(start, size)
    return subList(start, end)
}
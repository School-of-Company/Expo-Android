package com.school_of_company.common

import com.school_of_company.common.regex.isValidDate
import com.school_of_company.common.regex.isValidDateSequence
import com.school_of_company.common.regex.isValidDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DateValidationTest {

    @Test
    fun `test valid date sequence and related functions`() {
        // isValidDateSequence 관련 케이스
        val dateSequenceCases = listOf(
            Triple("20250116", "20250117", true),  // 올바른 날짜 순서
            Triple("20250117", "20250116", false), // 잘못된 날짜 순서
            Triple("", "", false),  // 빈값
            Triple("34232432432432432", "432432432432423432", false),  // 길이 초과
            Triple("20221301", "20230101", false),  // 잘못된 월 (2022년 13월)
            Triple("20230132", "20230101", false),  // 잘못된 일 (2023년 1월 32일)
            Triple("20201301", "20230101", false),  // 잘못된 연도 (2020년 13월)
            Triple("20250118", "20250117", false),  // 미래 날짜
            Triple("20250116", "20250116", true)   // 날짜가 같을 때
        )

        dateSequenceCases.forEach { (startDate, endDate, expected) ->
            assertEquals("Failed for startDate: $startDate, endDate: $endDate", expected, startDate.isValidDateSequence(endDate))
        }
    }

    @Test
    fun `test valid date validation`() {
        // isValidDate 관련 케이스
        val dateCases = listOf(
            "20250116" to true,  // 유효한 날짜
            "20250132" to false, // 잘못된 일 (2025년 1월 32일)
            "20251301" to false, // 잘못된 월 (2025년 13월)
            "abcd1234" to false, // 잘못된 형식
            "1234567" to false   // 길이 부족
        )

        dateCases.forEach { (date, expected) ->
            assertEquals("Failed for date: $date", expected, date.isValidDate())
        }
    }

    @Test
    fun `test valid date-time validation`() {
        // isValidDateTime 관련 케이스
        val dateTimeCases = listOf(
            "202501161200" to true,  // 유효한 날짜-시간
            "202501161260" to false, // 잘못된 시간 (2025년 1월 16일 1260분)
            "202501001200" to false, // 잘못된 날짜 (2025년 1월 0일)
            "abcd12345678" to false, // 잘못된 형식
            "12345678901" to false   // 길이 초과
        )

        dateTimeCases.forEach { (dateTime, expected) ->
            assertEquals("Failed for dateTime: $dateTime", expected, dateTime.isValidDateTime())
        }
    }

}


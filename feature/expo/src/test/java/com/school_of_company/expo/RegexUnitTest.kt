package com.school_of_company.expo

import com.school_of_company.common.regex.isValidDate
import com.school_of_company.common.regex.isValidDateTime
import org.junit.Assert.*
import org.junit.Test

class DateValidationTest {

    @Test
    fun `isValidDate should return correct results for various date strings`() {
        val validDates = listOf("20250116", "20000229")
        val invalidDates = listOf("20250132", "20251301", "abcd1234", "1234567")

        validDates.forEach { assertTrue(it.isValidDate()) }
        invalidDates.forEach { assertFalse(it.isValidDate()) }
    }

    @Test
    fun `isValidDateTime should return correct results for various datetime strings`() {
        val validDateTimes = listOf("202501161200", "200002291159", "202512312359")
        val invalidDateTimes = listOf("202501161260", "202501001200", "abcd12345678", "12345678901", "202513011200")

        validDateTimes.forEach { assertTrue(it.isValidDateTime()) }
        invalidDateTimes.forEach { assertFalse(it.isValidDateTime()) }
    }

    @Test
    fun `edge cases for isValidDate`() {
        val edgeCases = listOf(
            "" to false,
            "00000000" to false,
            "20200229" to true,
            "20201301" to false, // 잘못된 월
            "20200230" to false // 잘못된 일
        )

        edgeCases.forEach { (input, expected) ->
            assertEquals(expected, input.isValidDate())
        }
    }

    @Test
    fun `edge cases for isValidDateTime`() {
        val edgeCases = listOf(
            "" to false,
            "000000000000" to false,
            "202002291200" to true,
            "202002291259" to true, // 유효한 시간
            "202002291370" to false, // 잘못된 시간
            "202013012359" to false // 잘못된 월
        )

        edgeCases.forEach { (input, expected) ->
            assertEquals(expected, input.isValidDateTime())
        }
    }
}

package com.school_of_company.expo

import com.school_of_company.common.regex.isValidDate
import com.school_of_company.common.regex.isValidDateTime
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DateValidationTest {

    @Test
    fun `isValidDate should return true for valid date strings`() {
        // 유효한 날짜
        assertTrue("20250116".isValidDate()) // 정상적인 날짜
        assertTrue("20000229".isValidDate()) // 윤년 날짜
    }

    @Test
    fun `isValidDate should return false for invalid date strings`() {
        // 잘못된 날짜
        assertFalse("20250132".isValidDate()) // 잘못된 일(day)
        assertFalse("20251301".isValidDate()) // 잘못된 월(month)
        assertFalse("abcd1234".isValidDate()) // 숫자가 아닌 입력
        assertFalse("1234567".isValidDate())  // 길이가 맞지 않음
    }

    @Test
    fun `isValidDateTime should return true for valid datetime strings`() {
        // 유효한 날짜 및 시간
        assertTrue("202501161200".isValidDateTime()) // 정상적인 날짜와 시간
        assertTrue("200002291159".isValidDateTime()) // 윤년 날짜와 시간
        assertTrue("202512312359".isValidDateTime()) // 연말의 마지막 시간
    }

    @Test
    fun `isValidDateTime should return false for invalid datetime strings`() {
        // 잘못된 날짜 및 시간
        assertFalse("202501161260".isValidDateTime()) // 잘못된 분(minutes)
        assertFalse("202501001200".isValidDateTime()) // 잘못된 일(day)
        assertFalse("abcd12345678".isValidDateTime()) // 숫자가 아닌 입력
        assertFalse("12345678901".isValidDateTime())  // 길이가 맞지 않음
        assertFalse("202513011200".isValidDateTime()) // 잘못된 월(month)
    }

    @Test
    fun `edge cases for isValidDate and isValidDateTime`() {
        // 경계값 테스트
        assertFalse("".isValidDate()) // 빈 문자열
        assertFalse("".isValidDateTime()) // 빈 문자열
        assertFalse("00000000".isValidDate()) // 존재하지 않는 날짜
        assertFalse("000000000000".isValidDateTime()) // 존재하지 않는 날짜와 시간
        assertTrue("20200229".isValidDate()) // 윤년의 유효한 날짜
        assertTrue("202002291200".isValidDateTime()) // 윤년의 유효한 날짜와 시간
    }
}

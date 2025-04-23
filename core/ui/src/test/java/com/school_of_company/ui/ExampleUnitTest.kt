package com.school_of_company.ui

import com.school_of_company.ui.util.autoFormatToDateTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testAutoFormatToDateTime() {
        assertEquals("2025", "2025".autoFormatToDateTime())
        assertEquals("2025-01", "202501".autoFormatToDateTime())
        assertEquals("2025-01-14", "20250114".autoFormatToDateTime())
        assertEquals("2025-01-14 13:45", "202501141345".autoFormatToDateTime())
        assertEquals("", "".autoFormatToDateTime())
    }
}
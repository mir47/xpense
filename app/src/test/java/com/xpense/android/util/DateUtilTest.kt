package com.xpense.android.util

import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals

class DateUtilTest {
    @Test
    fun getFormattedDateString_givenDate_returnsCorrectFormat() {
        // GIVEN a date of 14 Feb 2022 in milliseconds
        val date = Date(1644862124109)

        // WHEN getFormattedDateString is called
        val result = getFormattedDateString(date)

        // THEN the date is returned in the correct format
        assertEquals("2022-02-14", result)
    }
}

package name.mharbovskyi.redditsimpleclient

import name.mharbovskyi.redditsimpleclient.data.toLocalDateTime
import name.mharbovskyi.redditsimpleclient.data.toSeconds
import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionsTest {
    @Test fun testUtcLongConvertsToLocalTime() {
        val time = 1540732270L
        val localDateTime = time.toLocalDateTime()
        assertEquals("year is not correct", 2018, localDateTime.year)
        assertEquals("day of month is not correct", 28, localDateTime.dayOfMonth)
        assertEquals("month is not correct", 10, localDateTime.monthValue)
        assertEquals("hour is not correct", 14, localDateTime.hour)
        assertEquals("minute is not correct", 11, localDateTime.minute)
    }

    @Test fun testConvertLocalDateWorksViceVersa() {
        val time = 1540732270L
        val localDateTime = time.toLocalDateTime()
        assertEquals(time, localDateTime.toSeconds())
    }
}

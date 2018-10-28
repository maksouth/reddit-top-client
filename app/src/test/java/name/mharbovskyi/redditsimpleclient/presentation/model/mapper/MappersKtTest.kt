package name.mharbovskyi.redditsimpleclient.presentation.model.mapper

import org.junit.Test

import org.junit.Assert.*

class MappersKtTest {

    @Test
    fun shortStringNumber() {
        assertEquals("13KK", shortStringNumber(13_000_000))
        assertEquals("7", shortStringNumber(7))
        assertEquals("3.2K", shortStringNumber(3200))
    }
}
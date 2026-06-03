package exercism

import lib.isDivisibleBy
import lib.isNotDivisibleBy
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/leap
 *
 * Leap
 * [Easy]
 *
 * Given a year, report if it is a leap year.
 */
class Year(year: Int) {

    val isLeap by lazy {
        (year isDivisibleBy 4 && year isNotDivisibleBy 100)
            || year isDivisibleBy 400
    }
}

/**
 * Unit tests
 */
class LeapYearTest {

    @Test
    fun yearNotDivisibleBy4() {
        assertFalse(Year(2015).isLeap)
    }

    @Test
    fun yearDivisibleBy4NotDivisibleBy100() {
        assertTrue(Year(1996).isLeap)
    }

    @Test
    fun yearDivisibleBy100NotDivisibleBy400() {
        assertFalse(Year(2100).isLeap)
    }

    @Test
    fun yearDivisibleBy400() {
        assertTrue(Year(2000).isLeap)
    }
}

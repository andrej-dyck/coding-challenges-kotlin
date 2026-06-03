package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/difference-of-squares
 *
 * Difference Of Squares
 * [Easy]
 *
 * Find the difference between the square of the sum and the sum of the squares
 * of the first N natural numbers.
 */
class Squares(number: Int) {

    /* Using Gauss' formula (cf. https://brilliant.org/wiki/sum-of-n-n2-or-n3/) */
    private val squareOfSum by lazy {
        (number * (number + 1) / 2).squared()
    }

    private val sumOfSquares by lazy {
        number * (number + 1) * (2 * number + 1) / 6
    }

    private val difference by lazy {
        squareOfSum - sumOfSquares
    }

    /* since the tests need these functions ... */
    fun squareOfSum() = squareOfSum

    fun sumOfSquares() = sumOfSquares

    fun difference() = difference
}

private fun Int.squared() = this * this

/**
 * Unit tests
 */
class DifferenceOfSquaresTest {

    @Test
    fun squareOfSum1() {
        assertEquals(1, Squares(1).squareOfSum())
    }

    @Test
    fun squareOfSum5() {
        assertEquals(225, Squares(5).squareOfSum())
    }

    @Test
    fun squareOfSum100() {
        assertEquals(25_502_500, Squares(100).squareOfSum())
    }

    @Test
    fun sumOfSquares1() {
        assertEquals(1, Squares(1).sumOfSquares())
    }

    @Test
    fun sumOfSquares5() {
        assertEquals(55, Squares(5).sumOfSquares())
    }

    @Test
    fun sumOfSquares100() {
        assertEquals(338_350, Squares(100).sumOfSquares())
    }

    @Test
    fun differenceOfSquares1() {
        assertEquals(0, Squares(1).difference())
    }

    @Test
    fun differenceOfSquares5() {
        assertEquals(170, Squares(5).difference())
    }

    @Test
    fun differenceOfSquares100() {
        assertEquals(25_164_150, Squares(100).difference())
    }
}

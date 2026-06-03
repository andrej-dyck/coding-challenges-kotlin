package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger

/**
 * https://exercism.io/tracks/kotlin/exercises/grains
 *
 * Grains
 * [Medium]
 *
 * Calculate the number of grains of wheat on a chessboard given that
 * the number on each square doubles.
 */
object Board {

    fun getGrainCountForSquare(square: Int) =
        GrainsOnSquare(square).value

    fun getTotalGrainCount() =
        SumOfGrainsUpTo(square = 64).value
}

class GrainsOnSquare(square: Int) :
    LargeInteger by PowerOfTwo(square - 1) {

    init {
        require(square in 1..64) {
            "Only integers between 1 and 64 (inclusive) are allowed"
        }
    }
}

class SumOfGrainsUpTo(square: Int) :
    LargeInteger by Difference(PowerOfTwo(square), ValueOf(1))

interface LargeInteger {
    val value: BigInteger
}

class PowerOfTwo(exponent: Int) :
    LargeInteger by ValueOf({ BigInteger.TWO.pow(exponent) })

class Difference(minuend: LargeInteger, subtrahend: LargeInteger) :
    LargeInteger by ValueOf({ minuend.value - subtrahend.value })

class ValueOf(lazyValue: () -> BigInteger) : LargeInteger {

    override val value by lazy(lazyValue)

    constructor(value: Int) : this({ value.toBigInteger() })
}

/**
 * Unit tests
 */
class BoardTest {

    @Test
    fun testSquare1ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.ONE, Board.getGrainCountForSquare(1))
    }

    @Test
    fun testSquare2ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.valueOf(2), Board.getGrainCountForSquare(2))
    }

    @Test
    fun testSquare3ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.valueOf(4), Board.getGrainCountForSquare(3))
    }

    @Test
    fun testSquare4ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.valueOf(8), Board.getGrainCountForSquare(4))
    }

    @Test
    fun testSquare16ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.valueOf(32_768), Board.getGrainCountForSquare(16))
    }

    @Test
    fun testSquare32ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger.valueOf(2_147_483_648), Board.getGrainCountForSquare(32))
    }

    @Test
    fun testSquare64ContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger("9223372036854775808"), Board.getGrainCountForSquare(64))
    }

    @Test
    fun testSquare0IsInvalid() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "Only integers between 1 and 64 (inclusive) are allowed"
        ) {
            Board.getGrainCountForSquare(0)
        }
    }

    @Test
    fun testNegativeSquareIsInvalid() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "Only integers between 1 and 64 (inclusive) are allowed"
        ) {
            Board.getGrainCountForSquare(-1)
        }
    }

    @Test
    fun testSquareGreaterThan64IsInvalid() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "Only integers between 1 and 64 (inclusive) are allowed"
        ) {
            Board.getGrainCountForSquare(65)
        }
    }

    @Test
    fun testBoardContainsCorrectNumberOfGrains() {
        assertEquals(BigInteger("18446744073709551615"), Board.getTotalGrainCount())
    }
}

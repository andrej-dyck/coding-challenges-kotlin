package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.io/tracks/kotlin/exercises/largest-series-product
 *
 * Largest Series Product
 * [Medium]
 *
 * Given a string of digits, calculate the largest product
 * for a contiguous substring of digits of length n.
 */
class Series(private val digits: List<Digit>) {

    constructor(digitsAsString: String) : this(digitsAsString.map(::Digit))

    fun largestProduct(numberOfDigits: Int): Long {
        require(numberOfDigits in 0..digits.size)
        return when (numberOfDigits) {
            0 -> 1L
            else -> digits.toProductsOfSeriesOf(numberOfDigits).maxOrNull() ?: 1L
        }
    }

    private fun List<Digit>.toProductsOfSeriesOf(numberOfDigits: Int) =
        windowed(numberOfDigits) { it.product() }

    private fun List<Digit>.product() =
        fold(1L) { product, digit -> product * digit.value }

    /*
     * 'get' is a horrible naming convention!
     * Since the exercism-tests need it, here a delegation.
     */
    fun getLargestProduct(numberOfDigits: Int) =
        largestProduct(numberOfDigits)
}

@JvmInline
value class Digit(val value: Int) {

    constructor(asChar: Char) : this(asChar.digitToInt())

    init {
        require(value in 0..9)
    }
}

/**
 * Unit tests
 */
class SeriesTest {

    @Test
    fun findsTheLargestProductIfSpanEqualsLength() {
        assertEquals(18, Series("29").getLargestProduct(2))
    }

    @Test
    fun findsTheLargestProductOf2WithNumbersInOrder() {
        assertEquals(72, Series("0123456789").getLargestProduct(2))
    }

    @Test
    fun findsTheLargestProductOf2() {
        assertEquals(48, Series("576802143").getLargestProduct(2))
    }

    @Test
    fun findsTheLargestProductOf3WithNumbersInOrder() {
        assertEquals(504, Series("0123456789").getLargestProduct(3))
    }

    @Test
    fun findsTheLargestProductOf3() {
        assertEquals(270, Series("1027839564").getLargestProduct(3))
    }

    @Test
    fun findsTheLargestProductOf5WithNumbersInOrder() {
        assertEquals(15120, Series("0123456789").getLargestProduct(5))
    }

    @Test
    fun findsTheLargestProductWithinABigNumber() {
        assertEquals(
            23520,
            Series("73167176531330624919225119674426574742355349194934").getLargestProduct(6)
        )
    }

    @Test
    fun reports0IfAllDigitsAre0() {
        assertEquals(0, Series("0000").getLargestProduct(2))
    }

    @Test
    fun reports0IfAllSpansInclude0() {
        assertEquals(0, Series("99099").getLargestProduct(3))
    }

    @Test
    fun rejectsSpanLongerThanStringLength() {
        assertThrows<java.lang.IllegalArgumentException> {
            Series("123").getLargestProduct(4)
        }
    }

    @Test
    fun reports1ForEmptyStringAndEmptyProduct() {
        assertEquals(1, Series("").getLargestProduct(0))
    }

    @Test
    fun reports1ForNonEmptyStringAndEmptyProduct() {
        assertEquals(1, Series("123").getLargestProduct(0))
    }

    @Test
    fun rejectsEmptyStringAndNonZeroSpan() {
        assertThrows<java.lang.IllegalArgumentException> {
            Series("").getLargestProduct(1)
        }
    }

    @Test
    fun rejectsInvalidCharacterInDigits() {
        assertThrows<java.lang.IllegalArgumentException> {
            Series("1234a5")
        }
    }

    @Test
    fun rejectsNegativeSpan() {
        assertThrows<java.lang.IllegalArgumentException> {
            Series("12345").getLargestProduct(-1)
        }
    }
}

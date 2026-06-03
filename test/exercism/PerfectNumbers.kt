package exercism

import lib.isDivisibleBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.io/tracks/kotlin/exercises/perfect-numbers
 *
 * Perfect Numbers
 * [Easy]
 *
 * Determine if a number is perfect, abundant, or deficient
 * based on Nicomachus' (60 - 120 CE) classification scheme for positive integers.
 */
fun classify(number: Int) = NaturalNumber(number).classification

enum class Classification {
    DEFICIENT, PERFECT, ABUNDANT
}

class NaturalNumber(private val value: Int) {

    init {
        require(value > 0) { "natural numbers are integers > 0" }
    }

    val classification: Classification by lazy {
        when {
            aliquotSum < value -> Classification.DEFICIENT
            aliquotSum > value -> Classification.ABUNDANT
            else -> Classification.PERFECT
        }
    }

    private val aliquotSum by lazy { aliquotSum() }

    private fun aliquotSum(): Int = properDivisors().sum()

    private fun properDivisors() =
        (1 until value).filter { value isDivisibleBy it }
}

/**
 * Unit tests
 */
class NaturalNumberTest {

    @Test
    fun smallPerfectNumberIsClassifiedCorrectly() {
        assertEquals(Classification.PERFECT, classify(6))
    }

    @Test
    fun mediumPerfectNumberIsClassifiedCorrectly() {
        assertEquals(Classification.PERFECT, classify(28))
    }

    @Test
    fun largePerfectNumberIsClassifiedCorrectly() {
        assertEquals(Classification.PERFECT, classify(33_550_336))
    }

    @Test
    fun smallAbundantNumberIsClassifiedCorrectly() {
        assertEquals(Classification.ABUNDANT, classify(12))
    }

    @Test
    fun mediumAbundantNumberIsClassifiedCorrectly() {
        assertEquals(Classification.ABUNDANT, classify(30))
    }

    @Test
    fun largeAbundantNumberIsClassifiedCorrectly() {
        assertEquals(Classification.ABUNDANT, classify(33_550_335))
    }

    @Test
    fun smallestPrimeDeficientNumberIsClassifiedCorrectly() {
        assertEquals(Classification.DEFICIENT, classify(2))
    }

    @Test
    fun smallestNonPrimeDeficientNumberIsClassifiedCorrectly() {
        assertEquals(Classification.DEFICIENT, classify(4))
    }

    @Test
    fun mediumNumberIsClassifiedCorrectly() {
        assertEquals(Classification.DEFICIENT, classify(32))
    }

    @Test
    fun largeDeficientNumberIsClassifiedCorrectly() {
        assertEquals(Classification.DEFICIENT, classify(33_550_337))
    }

    @Test
    fun edgeCaseWithNoFactorsOtherThanItselfIsClassifiedCorrectly() {
        assertEquals(Classification.DEFICIENT, classify(1))
    }

    @Test
    fun zeroIsNotANaturalNumber() {
        assertThrows<java.lang.RuntimeException> {
            classify(0)
        }
    }

    @Test
    fun negativeNumberIsNotANaturalNumber() {
        assertThrows<java.lang.RuntimeException> {
            classify(-1)
        }
    }
}

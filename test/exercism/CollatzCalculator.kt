package exercism

import lib.isEven
import lib.isOdd
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.io/tracks/kotlin/exercises/collatz-conjecture
 *
 * Collatz Conjecture
 * [Medium]
 *
 * Calculate the number of steps to reach 1 using the Collatz conjecture.
 */
object CollatzCalculator {

    fun computeStepCount(n: Int) =
        conjecture().sequence(n).count()

    private fun conjecture() =
        CollatzConjecture.Precondition(
            CollatzConjecture.Arithmetic()
//            CollatzConjecture.Binary()
        )
}

interface CollatzConjecture {

    fun sequence(n: Int): Sequence<Int>

    class Precondition(private val collatzConjecture: CollatzConjecture) : CollatzConjecture {

        override fun sequence(n: Int): Sequence<Int> {
            require(n > 0) {
                "Only natural numbers are allowed"
            }
            return collatzConjecture.sequence(n)
        }
    }

    class Arithmetic : CollatzConjecture {

        override fun sequence(n: Int) =
            generateSequence(n) {
                if (it.isEven()) it / 2
                else it * 3 + 1
            }.takeWhile {
                it != 1
            }
    }

    class Binary : CollatzConjecture {

        override fun sequence(n: Int) =
            generateSequence(n) {
                if (it.isOdd()) it.append1() + it
                else it shr 1
            }.takeWhile {
                it != 1
            }
    }
}

private fun Int.append1() = (this shl 1) or 1

/**
 * Test
 */
class CollatzCalculatorTest {

    @Test
    fun testZeroStepsRequiredWhenStartingFrom1() {
        assertEquals(0, CollatzCalculator.computeStepCount(1))
    }

    @Test
    fun testCorrectNumberOfStepsWhenAllStepsAreDivisions() {
        assertEquals(4, CollatzCalculator.computeStepCount(16))
    }

    @Test
    fun testCorrectNumberOfStepsWhenBothStepTypesAreNeeded() {
        assertEquals(9, CollatzCalculator.computeStepCount(12))
    }

    @Test
    fun testAVeryLargeInput() {
        assertEquals(152, CollatzCalculator.computeStepCount(1_000_000))
    }

    @Test
    fun testZeroIsConsideredInvalidInput() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "Only natural numbers are allowed"
        ) {
            CollatzCalculator.computeStepCount(0)
        }
    }

    @Test
    fun testNegativeIntegerIsConsideredInvalidInput() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "Only natural numbers are allowed"
        ) {
            CollatzCalculator.computeStepCount(-15)
        }
    }
}

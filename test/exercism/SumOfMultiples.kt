package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/sum-of-multiples
 *
 * Sum Of Multiples
 * [Medium]
 *
 * Given a number, find the sum of all the multiples of particular numbers up to but not including that number.
 */
interface SumOfMultiples {

    val sum: Int

    companion object {
        fun sum(numbers: Set<Int>, multiplesExclusiveUpperLimit: Int) =
            when (numbers.size) {
                in 0..3 -> ArithmeticSumOfMultiples(numbers, multiplesExclusiveUpperLimit - 1).sum
                else -> BrutForceSumOfMultiples(numbers, multiplesExclusiveUpperLimit - 1).sum
            }
    }
}

/**
 * Brute-force Approach
 */
class BrutForceSumOfMultiples(numbers: Set<Int>, maxMultiple: Int) : SumOfMultiples {

    /**
     * Idea: list all multiples (starting with the min number) and add up only those
     * that are divisible by any of the given numbers.
     */
    override val sum by lazy {
        numbers.minOrNull()?.let { min ->
            (min..maxMultiple).filter { numbers.anyIsDivisorOf(it) }.sum()
        } ?: 0
    }

    private fun Iterable<Int>.anyIsDivisorOf(multiple: Int) = any { multiple.isDivisibleBy(it) }

    private fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0
}

/**
 * A arithmetic/geometric approach to the problem
 *
 * Note that the sum of multiples of a number n is calculated as follows:
 * n*1 + n*2 + n*3 + ... OR n * (1 + 2 + 3 + ...)
 *
 * Since we have an upper limit L for the multiples, the sequence of multipliers
 * is limited by a max multiplier M as well; i.e.: n * (1 + 2 + 3 + ... + M)
 *
 * The second term, the sum of the multipliers sequence, can be determined by
 * Gauss' formula for arithmetic series: 1 + 2 + ... M == M * (M + 1) / 2
 *
 * Where M is the max. Multiplier for a n, such that n*M <= L -> M = L / n
 *
 * So, the sum of multiples of a number n, limited by (excluding) L, is:
 * n * (1 + 2 + 3 + ... + L / n) [=: S(n)]
 *
 * For two or three numbers only distinct multiples have to be considered considered.
 * Here, set theory (symmetric difference) is used to determine the sum of multiples.
 *
 * For two numbers n1, n2 its the sum of the individual sums S(n1) + S(n2) minus
 * the sum of "intersecting" multiples S(LCM(n1, n2)) where LCM is the
 * least common multiple.
 *
 * For three numbers n1, n2, n3 its the sum of the individual sums
 * S(n1) + S(n2) + S(n3) minus the sum of "intersecting" multiples
 * S(LCM(n1, n2) + S(LMC(n2, n3) + S(LMC(n1, n2, n3)).
 *
 * @param numbers A set of 0 to 3 numbers
 * @param maxMultiple The max. multiple considered for the sum (upper limit L)
 */
class ArithmeticSumOfMultiples(numbers: Set<Int>, maxMultiple: Int) : SumOfMultiples {

    init {
        require(numbers.size <= 3) {
            "arithmetic solution only works for up to three numbers"
        }
        require(numbers.all { it >= 0 } && maxMultiple >= 0) {
            "only natural numbers possible"
        }
    }

    /**
     * Sum of (distinct) multiples (smaller that the max multiple) of numbers using
     * set theory [symm. diff.].
     */
    override val sum by lazy {
        numbers.sumOfAllMultiplesUpTo(maxMultiple) - numbers.sumOfCommonMultiplesUpTo(maxMultiple)
    }

    /**
     * Sum of all multiples: S(n1) + S(n2) + S(n3) + ...
     */
    private fun Set<Int>.sumOfAllMultiplesUpTo(maxMultiple: Int) =
        sumOf { sumOfMultiplesOfUpTo(it, maxMultiple) }

    /**
     * Sum of n*(1..N) where N = (L-1) / n
     */
    private fun sumOfMultiplesOfUpTo(n: Int, maxMultiple: Int) =
        n * sumOfNaturalNumbersUpTo(maxMultiple / n)

    /**
     * Sum of 1..n using the Gauss' formula
     */
    private fun sumOfNaturalNumbersUpTo(n: Int) = n * (n + 1) / 2

    /**
     * Sum of intersecting multiples
     * For empty sets or sets of one number, this sum is 0.
     * For two numbers: S(LCM(n1, n2) where LCM is the least common multiple
     * For three numbers: S(LCM(n1, n2) + S(LMC(n2, n3) + S(LMC(n1, n2, n3))
     *
     * FIXME this implementation doesn't work for more than three numbers!
     */
    private fun Set<Int>.sumOfCommonMultiplesUpTo(maxMultiple: Int) =
        if (size == 0) 0
        else (2..size).sumOf {
            windowed(it).sumOf { slice ->
                sumOfMultiplesOfUpTo(slice.lcm(), maxMultiple)
            }
        }
}

/**
 * Math Stuff
 */
internal fun Collection<Int>.lcm() = this.reduce { acc: Int, i: Int -> lcm(acc, i) }
internal fun lcm(a: Int, b: Int) = a / gcd(a, b) * b
internal tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

/**
 * Unit tests
 */
class SumOfMultiplesTest {

    @Test
    fun `multiples of 3 or 5 up to 1`() {
        assertEquals(0, SumOfMultiples.sum(setOf(3, 5), 1))
    }

    @Test
    fun `multiples of 3 or 5 up to 4`() {
        assertEquals(3, SumOfMultiples.sum(setOf(3, 5), 4))
    }

    @Test
    fun `multiples of 3 up to 7`() {
        assertEquals(9, SumOfMultiples.sum(setOf(3), 7))
    }

    @Test
    fun `multiples of 3 or 5 up to 10`() {
        assertEquals(23, SumOfMultiples.sum(setOf(3, 5), 10))
    }

    @Test
    fun `multiples of 3 or 5 up to 100`() {
        assertEquals(2318, SumOfMultiples.sum(setOf(3, 5), 100))
    }

    @Test
    fun `multiples of 3 or 5 up to 1000`() {
        assertEquals(233_168, SumOfMultiples.sum(setOf(3, 5), 1000))
    }

    @Test
    fun `multiples of 7, 13 or 17 up to 20`() {
        assertEquals(51, SumOfMultiples.sum(setOf(7, 13, 17), 20))
    }

    @Test
    fun `multiples of 4 or 6 up to 15`() {
        assertEquals(30, SumOfMultiples.sum(setOf(4, 6), 15))
    }

    @Test
    fun `multiples of 5, 6 or 8 up to 150`() {
        assertEquals(4419, SumOfMultiples.sum(setOf(5, 6, 8), 150))
    }

    @Test
    fun `multiples of 5 or 25 up to 51`() {
        assertEquals(275, SumOfMultiples.sum(setOf(5, 25), 51))
    }

    @Test
    fun `multiples of 43 or 47 up to 10000`() {
        assertEquals(2_203_160, SumOfMultiples.sum(setOf(43, 47), 10000))
    }

    @Test
    fun `multiples of 1 up to 100`() {
        assertEquals(4950, SumOfMultiples.sum(setOf(1), 100))
    }

    @Test
    fun `multiples of an empty set up to 10000`() {
        assertEquals(0, SumOfMultiples.sum(emptySet(), 10000))
    }
}

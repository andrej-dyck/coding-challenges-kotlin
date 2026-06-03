package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

/**
 * https://exercism.io/tracks/kotlin/exercises/sieve
 *
 * Sieve
 * [Easy]
 *
 * Use the Sieve of Eratosthenes to find all the primes from 2 up to a given number.
 */
interface Sieve {

    fun primes(): Sequence<Int>

    companion object {
        fun primesUpTo(n: Int) =
            BoundedSieveOfEratosthenes(n).primes().take(n).toList()
    }
}

class BoundedSieveOfEratosthenes(private val maxNumber: Int) : Sieve {

    init {
        require(maxNumber >= 0)
    }

    override fun primes() = lazyPrimes

    private val lazyPrimes by lazy {
        when {
            maxNumber < 2 -> emptySequence()
            else -> sequenceOf(2) + sieved(oddNumbers)
        }
    }

    private val oddNumbers by lazy {
        (3..maxNumber step 2).asSequence()
    }

    private fun sieved(oddNumbers: Sequence<Int>) =
        oddNumbers.minus(multiplesOf(oddNumbers))

    private fun multiplesOf(oddNumbers: Sequence<Int>): Sequence<Int> {
        val sqrtOfMax = sqrt(maxNumber.toDouble()).toInt()

        return oddNumbers
            // refinement; works only with refinement below
            .takeWhile { it <= sqrtOfMax }
            // refinement: starting multiples with it^2 as smaller multiples of p will
            // have already been marked and steps of it*2 as only odd numbers
            // are considered
            .flatMap { (it * it..maxNumber step it * 2).asSequence() }
    }
}

/**
 * Unit tests
 */
class SieveTest {

    @Test
    fun noPrimesUnder2() {
        val expectedOutput = emptyList<Int>()

        assertEquals(expectedOutput, Sieve.primesUpTo(1))
    }

    @Test
    fun findFirstPrime() {
        val expectedOutput = listOf(2)

        assertEquals(expectedOutput, Sieve.primesUpTo(2))
    }

    @Test
    fun findPrimesUpTo10() {
        val expectedOutput = listOf(2, 3, 5, 7)

        assertEquals(expectedOutput, Sieve.primesUpTo(7))
    }

    @Test
    fun findPrimesUpTo1000() {
        val expectedOutput = listOf(
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
            101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199,
            211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397,
            401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499,
            503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599,
            601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691,
            701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797,
            809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887,
            907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997
        )
        assertEquals(expectedOutput, Sieve.primesUpTo(1000))
    }
}

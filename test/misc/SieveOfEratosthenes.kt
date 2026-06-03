package misc

import lib.IntArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
 *
 * Sieve of Eratosthenes
 */
fun primeNumbers(): Sequence<Int> {
    fun sieve(numbers: Sequence<Int>): Sequence<Int> = generateSequence {
        val (head, tail) = numbers.first() to numbers.drop(1)
        sequenceOf(head) + sieve(tail.filter { it % head > 0 })
    }.flatten()

    return sieve(naturalNumbers(2))
}

fun naturalNumbers(start: Int = 1) =
    generateSequence(start) { it + 1 }

/**
 * Unit tests
 */
class SieveOfEratosthenesTest {

    @ParameterizedTest
    @CsvSource(
        "0; []",
        "1; [2]",
        "5; [2, 3, 5, 7, 11]",
        "17; [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59]",
        delimiter = ';'
    )
    fun `sequence of first n primes`(
        n: Int,
        @ConvertWith(IntArrayArg::class) primes: Array<Int>
    ) {
        Assertions.assertThat(
            primeNumbers().take(n).toList()
        ).containsExactlyElementsOf(
            primes.asIterable()
        )
    }
}

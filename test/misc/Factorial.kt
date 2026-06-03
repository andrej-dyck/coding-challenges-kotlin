package misc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://en.wikipedia.org/wiki/Factorial
 *
 * Factorial
 */
fun Int.factorial() = (1..this).product()

fun IntRange.product() = fold(1L) { a, n -> a * n }

/**
 * Unit tests
 */
class FactorialTest {

    @ParameterizedTest
    @CsvSource(
        "0, 1",
        "1, 1",
        "2, 2",
        "5, 120",
        "10, 3628800"
    )
    fun `factorial is the product of all natural numbers up to n`(n: Int, factorial: Long) {
        assertThat(
            n.factorial()
        ).isEqualTo(
            factorial
        )
    }
}

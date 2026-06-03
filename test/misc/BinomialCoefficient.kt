package misc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://en.wikipedia.org/wiki/Binomial_coefficient
 *
 * Binomial Coefficient
 */
fun Int.chose(k: Int) = binomialCoefficient(this, k)

fun binomialCoefficient(n: Int, k: Int) =
    ((1 + n - k)..n).product() / k.factorial()

/**
 * Unit tests
 */
class BinomialCoefficientTest {

    @ParameterizedTest
    @CsvSource(
        "0, 0, 1",
        "1, 0, 1",
        "1, 1, 1",
        "2, 1, 2",
        "6, 0, 1",
        "6, 1, 6",
        "6, 2, 15",
        "6, 3, 20",
        "6, 4, 15",
        "6, 1, 6",
        "6, 6, 1"
    )
    fun `nCk is factorial(n) divided by (factorial(k) times factorial(n-k))`(
        n: Int,
        k: Int,
        expectedCoefficient: Long
    ) {
        assertThat(
            n.chose(k)
        ).isEqualTo(
            expectedCoefficient
        )
    }
}

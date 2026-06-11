package exercism

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://exercism.org/tracks/kotlin/exercises/isogram
 *
 * Isogram
 * [Easy]
 *
 * Determine if a word or phrase is an isogram.
 */
object Isogram {

    fun isIsogram(input: String): Boolean {
        val seen = mutableSetOf<Char>()

        return input
            .asSequence()
            .filter(Char::isLetter)
            .all { seen.add(it.lowercaseChar()) }
    }
}

/**
 * Unit tests
 */
class IsogramTest {

    @ParameterizedTest
    @CsvSource(
        "'', true",
        "isogram, true",
        "eleven, false",
        "angola, false",
        "a, true",
        "moOse, false",
        "thumbscrew-japingly, true",
        "thumbscrew-jappingly, false",
        "Emily Jung Schwartzkopf, true",
    )
    fun `is isogram`(input: String, expected: Boolean) {
        assertThat(Isogram.isIsogram(input)).isEqualTo(expected)
    }
}

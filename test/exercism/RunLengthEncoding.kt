package exercism

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://exercism.org/tracks/kotlin/exercises/run-length-encoding
 *
 * Run Length Encoding
 * [Easy]
 *
 * Implement run-length encoding and decoding.
 */
object RunLengthEncoding {

    fun encode(input: String) =
        input.replace(letterAppearanceRegex) {
            "${it.value.length}${it.value.first()}"
        }

    private val letterAppearanceRegex = """([ a-zA-Z])\1+""".toRegex()

    fun decode(input: String) =
        input.replace(compressedLetterRegex) {
            it.destructured.let { (n, l) -> l.repeat(n.toInt()) }
        }

    private val compressedLetterRegex = """(\d+)([ a-zA-Z])""".toRegex()
}

/**
 * Unit tests
 */
class RunLengthEncodingTest {

    @Test
    fun `encodes empty string`() {
        assertThat(RunLengthEncoding.encode("")).isEqualTo("")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "ab",
            "aba",
            "xyz"
        ]
    )
    fun `encoded string is same if all subsequent letters are different`(input: String) {
        assertThat(RunLengthEncoding.encode(input)).isEqualTo(input)
    }

    @ParameterizedTest
    @CsvSource(
        "aa, 2a",
        "aaa, 3a",
        "aaaaaaaaaaaa, 12a",
        "aabbaa, 2a2b2a",
        "aabaa, 2ab2a",
        "aabbbcccc, 2a3b4c",
    )
    fun `encoded string conflates subsequent equal letters as count + letter`(
        input: String,
        expectedEncoding: String
    ) {
        assertThat(RunLengthEncoding.encode(input)).isEqualTo(expectedEncoding)
    }

    @ParameterizedTest
    @CsvSource(
        "a  a, a2 a",
        "'   a   ', '3 a3 '",
    )
    fun `encoded string conflates subsequent spaces count + space`(
        input: String,
        expectedEncoding: String
    ) {
        assertThat(RunLengthEncoding.encode(input)).isEqualTo(expectedEncoding)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "a",
            "ab",
            "aba",
            "xyz",
            "aa",
            "aaa",
            "aaaaaaaaaaaa",
            "aabbaa",
            "aabaa",
            "aabbbcccc",
            "a  a",
            "   a   ",
        ]
    )
    fun `decode is the reverse operation of encoding`(input: String) {
        assertThat(
            RunLengthEncoding.decode(
                RunLengthEncoding.encode(input)
            )
        ).isEqualTo(input)
    }
}

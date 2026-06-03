package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://leetcode.com/problems/string-to-integer-atoi/
 *
 * 8. String to Integer (atoi)
 * [Medium]
 *
 * Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer
 * (similar to C/C++'s atoi function).
 *
 * The algorithm for myAtoi(string s) is as follows:
 * 1. Read in and ignore any leading whitespace.
 * 2. Check if the next character (if not already at the end of the string) is '-' or '+'.
 *    Read this character in if it is either. This determines if the final result is
 *    negative or positive respectively. Assume the result is positive if neither is present.
 * 3. Read in next the characters until the next non-digit character or the end of the input is reached.
 *    The rest of the string is ignored.
 * 4. Convert these digits into an integer (i.e. "123" -> 123, "0032" -> 32).
 *    If no digits were read, then the integer is 0. Change the sign as necessary (from step 2).
 * 5. If the integer is out of the 32-bit signed integer range [-2^31, 2^31 - 1], then clamp the integer
 *    so that it remains in the range. Specifically, integers less than -2^31 should be clamped to -2^31,
 *    and integers greater than 231 - 1 should be clamped to 231 - 1.
 * 6. Return the integer as the final result.
 *
 * Note:
 * - Only the space character ' ' is considered a whitespace character.
 * - Do not ignore any characters other than the leading whitespace or the rest of the string after the digits.
 *
 * Constraints:
 * - 0 <= s.length <= 200
 * - s consists of English letters (lower-case and upper-case), digits (0-9), ' ', '+', '-', and '.'.
 */
fun myAtoi(string: String): Int =
    string
        .trimLeadingSpaces()
        .trimEnd { !it.isDigit() }
        .toLongOr { 0 }
        .clampToInt()

private fun String.trimLeadingSpaces() = trimStart(' ')

private fun String.toLongOr(otherwise: () -> Long) =
    if (matches("^[+\\-]?\\d+$".toRegex())) toLong()
    else otherwise()

fun Long.clampToInt() = when {
    this < Int.MIN_VALUE -> Int.MIN_VALUE
    this > Int.MAX_VALUE -> Int.MAX_VALUE
    else -> toInt()
}

/**
 * Unit tests
 */
class StringToIntegerTest {

    @ParameterizedTest
    @CsvSource(
        "42, 42",
        "-42, -42",
        "+42, +42",
        "0042, 42"
    )
    fun `parses string to integer with signs and leading zeros`(s: String, expectedInt: Int) {
        assertThat(myAtoi(s)).isEqualTo(expectedInt)
    }

    @Test
    fun `ignores leading spaces`() {
        assertThat(myAtoi("    -42")).isEqualTo(-42)
    }

    @Test
    fun `string without digits is 0`() {
        assertThat(myAtoi("")).isEqualTo(0)
    }

    @ParameterizedTest
    @CsvSource(
        "4193 with words, 4193",
        "4193!, 4193",
        "'4193 ', 4193",
        "4193..., 4193",
    )
    fun `ignores non digits at the end of string`(s: String, expectedInt: Int) {
        assertThat(myAtoi(s)).isEqualTo(expectedInt)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "words and 987",
            "\t987",
            "...987"
        ]
    )
    fun `string with a number that follow anything else then spaces, is parsed to 0`(s: String) {
        assertThat(myAtoi(s)).isEqualTo(0)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "-91283472332",
            "-2147483649"
        ]
    )
    fun `numbers that are smaller than min-integer, are clamped to min-integer`(s: String) {
        assertThat(myAtoi(s)).isEqualTo(Int.MIN_VALUE)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "91283472331",
            "2147483648"
        ]
    )
    fun `numbers that are larger than max-integer, are clamped to max-integer`(s: String) {
        assertThat(myAtoi(s)).isEqualTo(Int.MAX_VALUE)
    }
}

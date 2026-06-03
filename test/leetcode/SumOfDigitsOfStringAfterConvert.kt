package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/sum-of-digits-of-string-after-convert/
 *
 * 1945. Sum of Digits of String After Convert
 * [Easy]
 *
 * You are given a string s consisting of lowercase English letters, and an integer k.
 *
 * First, convert s into an integer by replacing each letter with its position in the alphabet
 * (i.e., replace 'a' with 1, 'b' with 2, ..., 'z' with 26). Then, transform the integer
 * by replacing it with the sum of its digits. Repeat the transform operation k times in total.
 *
 * For example, if s = "zbax" and k = 2, then the resulting integer would be 8 by the following operations:
 * - convert: "zbax" ➝ "(26)(2)(1)(24)" ➝ "262124" ➝ 262124
 * - transform #1: 262124 ➝ 2 + 6 + 2 + 1 + 2 + 4 ➝ 17
 * - transform #2: 17 ➝ 1 + 7 ➝ 8
 *
 * Return the resulting integer after performing the operations described above.
 *
 * Constraints:
 * - 1 <= s.length <= 100
 * - 1 <= k <= 10
 * - s consists of lowercase English letters.
 */
fun getLucky(word: String, k: Int = 0): Int =
    sumOfDigits(
        alphabetPositions(word).joinToString(""),
        iterations = k
    ).toIntOrNull() ?: 0

/* convert */
private fun alphabetPositions(word: String) = word.mapNotNull(Char::alphabetPosition)

fun Char.isAlphabetLetter() = this in 'a'..'z'
fun Char.alphabetPosition() = if (isAlphabetLetter()) code - 'a'.code + 1 else null

/* transform */
private fun sumOfDigits(number: String, iterations: Int) =
    generateSequence(number) { it.sumOf(Char::digitToInt).toString() }
        .take(iterations + 1)
        .last()

/**
 * Unit Tests
 */
class SumOfDigitsOfStringAfterConvertTest {

    @ParameterizedTest
    @CsvSource(
        "'', 0",
        "a, 1",
        "b, 2",
        "c, 3",
        "z, 26",
        "abc, 123",
        "xyz, 242526"
    )
    fun `with k = 0, it just converts to lower-case letters alphabet positions`(
        englishLetters: String,
        expectedSum: Int
    ) {
        assertThat(getLucky(englishLetters)).isEqualTo(expectedSum)
    }

    @ParameterizedTest
    @CsvSource(
        "'', 0",
        "a, 1",
        "b, 2",
        "c, 3",
        "z, 8", // 26 -> 2+6 -> 8
        "abc, 6",
        "xyz, 21" // 24,25,26 -> 2+4+2+5+2+6 -> 21
    )
    fun `with k = 1, it's the sum of digits after conversion`(
        englishLetters: String,
        expectedSum: Int
    ) {
        assertThat(getLucky(englishLetters, k = 1)).isEqualTo(expectedSum)
    }

    @ParameterizedTest
    @CsvSource(
        "'', 5, 0",
        "a, 0, 1",
        "a, 2, 1",
        "z, 2, 8", // 26 -> 2+6 -> 8
        "xyz, 2, 3", // 24,25,26 -> 2+4+2+5+2+6 -> 21 -> 2+1 -> 3
        "iiii, 1, 36", // 9,9,9,9 -> 9+9+9+9 -> 36
        "iiii, 2, 9", // 9,9,9,9 -> 9+9+9+9 -> 36 -> 3+6 -> 9
        "iiii, 3, 9", // 9,9,9,9 -> 9+9+9+9 -> 36 -> 3+6 -> 9
        "leetcode, 2, 6", // 12,5,5,20,3,15,4,5 -> 1+2+5+5+2+0+3+1+5+4+5 ➝ 33 -> 3+3 -> 6
        "zbax, 2, 8", // 26,2,1,24 -> 2+6+2+1+2+4 ➝ 17 -> 1+7 -> 8
        "abcdefghijklmnopqrstuvwxyz, 10, 9"
    )
    fun `with k greater than 1, it's the sum of digits of the result of previous conversion`(
        englishLetters: String,
        k: Int,
        expectedSum: Int
    ) {
        assertThat(getLucky(englishLetters, k)).isEqualTo(expectedSum)
    }

    @ParameterizedTest
    @CsvSource(
        "A, 0",
        "1, 0",
        "!, 0",
        "' ', 0",
        "a a, 11",
        "a1a2b3, 112",
    )
    fun `ignores all non-alphabet characters`(s: String, expectedSum: Int) {
        assertThat(getLucky(s)).isEqualTo(expectedSum)
    }
}

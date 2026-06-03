package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/maximum-number-of-words-you-can-type/
 *
 * 1935. Maximum Number of Words You Can Type
 * [Easy]
 *
 * There is a malfunctioning keyboard where some letter keys do not work.
 * All other keys on the keyboard work properly.
 *
 * Given a string text of words separated by a single space (no leading or trailing spaces)
 * and a string brokenLetters of all distinct letter keys that are broken, return the number
 * of words in text you can fully type using this keyboard.
 *
 * Constraints:
 * - 1 <= text.length <= 10^4
 * - 0 <= brokenLetters.length <= 26
 * - text consists of words separated by a single space without any leading or trailing spaces.
 * - Each word only consists of lowercase English letters.
 * - brokenLetters consists of distinct lowercase English letters.
 */
fun maxWordsThatCanBeTyped(text: String, brokenLetters: String): Int =
    maxWordsThatCanBeTyped(text.lowercase(), brokenLetters.toHashSet())

fun maxWordsThatCanBeTyped(text: String, brokenLetters: Set<Char>) =
    text.split(' ').count { it.containsNone(brokenLetters) }

fun String.containsNone(other: Set<Char>) =
    toHashSet().intersect(other).none()

/**
 * Unit tests
 */
class MaxWordsThatCanBeTypedTest {

    @ParameterizedTest
    @CsvSource(
        "a, '', 1",
        "a, a, 0",
        "the quick brown fox jumps over the lazy dog, '', 9",
        "the quick brown fox jumps over the lazy dog, abcdefghijklmnopqrstuvwxyz, 0",
        "the quick brown fox jumps over the lazy dog, t, 7",
        "hello world, ad, 1",
        "leet code, lt, 1",
        "leet code, e, 0"
    )
    fun `max number of words you can type of the text with the broken-keys`(
        text: String,
        brokenLetters: String,
        expectedWords: Int
    ) {
        assertThat(
            maxWordsThatCanBeTyped(text, brokenLetters)
        ).isEqualTo(
            expectedWords
        )
    }
}

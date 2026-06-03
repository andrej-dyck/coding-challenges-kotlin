package leetcode

import lib.maxOr
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/
 *
 * 1456. Maximum Number of Vowels in a Substring of Given Length
 * [Medium]
 *
 * Given a string s and an integer k.
 *
 * Return the maximum number of vowel letters in any substring of s with length k.
 *
 * Vowel letters in English are (a, e, i, o, u)
 *
 * Constraints:
 * - 1 <= s.length <= 10^5
 * - s consists of lowercase English letters.
 * - 1 <= k <= s.length
 */
fun maxNumOfVowels(s: String, k: Int, vowels: String = "aeiou") =
    s.windowed(k) {
        it.count { c -> c in vowels }
    }.maxOr { 0 }

/**
 * Unit tests
 */
class MaximumNumberOfVowelsTest {

    @ParameterizedTest
    @CsvSource(
        "abciiidef, 3, 3",
        "aeiou, 2, 2",
        "leetcode, 3, 2",
        "rhythms, 4, 0",
        "tryhard, 4, 1"
    )
    fun `maximum number of vowel letters in any substring of s with length k`(
        s: String,
        k: Int,
        expectedNumber: Int
    ) {
        println("s = $s, k = $k")

        assertThat(
            maxNumOfVowels(s, k)
        ).isEqualTo(
            expectedNumber
        )
    }
}

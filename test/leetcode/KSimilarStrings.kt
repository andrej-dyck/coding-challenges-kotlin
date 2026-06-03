package leetcode

import lib.minOr
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/k-similar-strings/
 *
 * 854. K-Similar Strings
 * [Hard]
 *
 * Strings A and B are K-similar (for some non-negative integer K) if we can swap
 * the positions of two letters in A exactly K times so that the resulting string equals B.
 *
 * Given two anagrams A and B, return the smallest K for which A and B are K-similar.
 *
 * Note:
 * - 1 <= A.length == B.length <= 20
 * - A and B contain only lowercase letters from the set {'a', 'b', 'c', 'd', 'e', 'f'}
 */
fun kSimilarity(a: String, b: String): Int {
    require(a.isAnagramOf(b))

    fun rec(a: String, b: String): Int = when {
        a == b -> 0
        a[0] == b[0] -> rec(a.drop(1), b.drop(1))
        else -> b.indices
            .filter { i -> a[0] == b[i] }
            .map { 1 + rec(a.drop(1), b.swap(0, it).drop(1)) }
            .minOr { 0 }
    }

    return rec(a, b)
}

fun String.isAnagramOf(other: String) =
    length == other.length && sortedChars().contentEquals(other.sortedChars())

private fun String.sortedChars() = toCharArray().sortedArray()

private fun String.swap(i1: Int, i2: Int) =
    if (i1 == i2) this
    else this.replace(i1, this[i2]).replace(i2, this[i1])

private fun String.replace(i: Int, c: Char) = replaceRange(i..i, c.toString())

/**
 * Unit tests
 */
class KSimilarStringsTest {

    @ParameterizedTest
    @CsvSource(
        "'', '', 0",
        "ab, ab, 0",
        "ab, ba, 1",
        "abc, bca, 2",
        "abac, baca, 2",
        "aabc, abca, 2"
    )
    fun `k-similarity of string a and b`(a: String, b: String, expectedKSimilarity: Int) {
        assertThat(
            kSimilarity(a, b)
        ).isEqualTo(
            expectedKSimilarity
        )
    }
}

package leetcode

import lib.StringArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/longest-common-prefix/
 *
 * 14. Longest Common Prefix
 * [Easy]
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 *
 * Constraints:
 * - 0 <= strs.length <= 200
 * - 0 <= strs[i].length <= 200
 * - strs[i] consists of only lower-case English letters.
 */
fun longestCommonPrefix(words: Array<String>) =
    words
        .map { it.asSequence() }
        .transpose()
        .takeWhile { it.distinct().size == 1 }
        .map { it.first() }
        .joinToString("")

fun <T> Iterable<Sequence<T>>.transpose(): Sequence<List<T>> {
    val iterators = map { it.iterator() }

    return sequence {
        while (iterators.all { it.hasNext() }) {
            yield(iterators.map { it.next() })
        }
    }
}

/**
 * Unit tests
 */
class LongestCommonPrefixTest {

    @ParameterizedTest
    @CsvSource(
        "[]; ''",
        "[flower]; flower",
        "[flower, flow]; flow",
        "[flower, flow, flight]; fl",
        "[dog, racecar, car]; ''",
        delimiter = ';'
    )
    fun `longest common prefix of all words`(
        @ConvertWith(StringArrayArg::class) words: Array<String>,
        expectedPrefix: String,
    ) {
        Assertions.assertThat(
            longestCommonPrefix(words)
        ).isEqualTo(
            expectedPrefix
        )
    }
}

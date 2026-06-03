package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/truncate-sentence/
 *
 * 1816. Truncate Sentence
 * [Easy]
 *
 * A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
 * Each of the words consists of only uppercase and lowercase English letters (no punctuation).
 *
 * For example, "Hello World", "HELLO", and "hello world hello world" are all sentences.
 *
 * You are given a sentence s and an integer k. You want to truncate s such that
 * it contains only the first k words. Return s after truncating it.
 *
 * Constraints:
 * - 1 <= s.length <= 500
 * - k is in the range [1, the number of words in s].
 * - s consist of only lowercase and uppercase English letters and spaces.
 * - The words in s are separated by a single space.
 * - There are no leading or trailing spaces.
 */
fun truncateSentence(s: String, k: Int) =
    s.splitToSequence(' ').take(k).joinToString(" ")

/**
 * Unit Tests
 */
class TruncateSentenceTest {

    @ParameterizedTest
    @CsvSource(
        "Hello how are you Contestant; 4; Hello how are you",
        "What is the solution to this problem; 4; What is the solution",
        "chopper is not a tanuki; 5; chopper is not a tanuki",
        delimiter = ';'
    )
    fun `truncated sentence`(sentence: String, k: Int, firstKWords: String) {
        assertThat(
            truncateSentence(sentence, k)
        ).isEqualTo(
            firstKWords
        )
    }
}

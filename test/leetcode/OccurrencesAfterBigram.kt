package leetcode

import lib.StringArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/occurrences-after-bigram/
 *
 * 1078. Occurrences After Bigram
 * [Easy]
 *
 * Given words first and second, consider occurrences in some text of the form "first second third",
 * where second comes immediately after first, and third comes immediately after second.
 *
 * For each such occurrence, add "third" to the answer, and return the answer.
 *
 * Note:
 * - 1 <= text.length <= 1000
 * - text consists of space separated words, where each word consists of lowercase English letters.
 * - 1 <= first.length, second.length <= 10
 * - first and second consist of lowercase English letters.
 */
fun wordsDirectlyAfter(text: String, firstWord: String, secondWord: String) =
    text.split(' ')
        .windowed(3)
        .filter { (w1, w2, _) -> w1 == firstWord && w2 == secondWord }
        .map { it.last() }

/**
 * Unit tests
 */
class OccurrencesAfterBigramTest {

    @ParameterizedTest
    @CsvSource(
        "alice is a good girl she is a good student; a; good; [girl, student]",
        "we will we will rock you; we; will; [we, rock]",
        delimiter = ';'
    )
    fun `words directly after w1 and w2 in text`(
        text: String,
        w1: String,
        w2: String,
        @ConvertWith(StringArrayArg::class) expectedWords: Array<String>
    ) {
        assertThat(
            wordsDirectlyAfter(text, w1, w2)
        ).containsExactlyElementsOf(
            expectedWords.asIterable()
        )
    }
}

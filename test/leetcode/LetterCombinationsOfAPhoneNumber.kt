package leetcode

import lib.StringArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 *
 * 17. Letter Combinations of a Phone Number
 * [Medium]
 *
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations
 * that the number could represent.
 * Return the answer in any order.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
 * Note that 1 does not map to any letters.
 *
 * Constraints:
 * - 0 <= digits.length <= 4
 * - digits[i] is a digit in the range ['2', '9'].
 */
fun letterCombinations(digits: String) = when {
    digits.none() -> emptySequence()
    else -> digits.asSequence().map(::digitLetters).combinations()
}

fun Sequence<String>.combinations(): Sequence<String> = when {
    none() -> sequenceOf("")
    else -> drop(1).combinations().flatMap { l ->
        first().map { "$it$l" }
    }
}

fun digitLetters(digit: Char) = when (digit) {
    '2' -> "abc"
    '3' -> "def"
    '4' -> "ghi"
    '5' -> "jkl"
    '6' -> "mno"
    '7' -> "pqrs"
    '8' -> "tuv"
    '9' -> "wxyz"
    '0' -> " "
    else -> ""
}

/**
 * Unit Tests
 */
class LetterCombinationsOfAPhoneNumberTest {

    @ParameterizedTest
    @CsvSource(
        "''; []",
        "2; [a,b,c]",
        "3; [d,e,f]",
        "4; [g,h,i]",
        "5; [j,k,l]",
        "6; [m,n,o]",
        "7; [p,q,r,s]",
        "8; [t,u,v]",
        "9; [w,x,y,z]",
        "0; [' ']",
        "23; [ad,ae,af,bd,be,bf,cd,ce,cf]",
        delimiter = ';'
    )
    fun `all combinations of phone digits to letters`(
        digits: String,
        @ConvertWith(StringArrayArg::class) expectedLetterCombinations: Array<String>
    ) {
        assertThat(
            letterCombinations(digits).toList()
        ).containsExactlyInAnyOrderElementsOf(
            expectedLetterCombinations.toList()
        )
    }

    @ParameterizedTest
    @CsvSource(
        "222; abc",
        "43556; hello",
        "568546; kotlin",
        "435560568546; hello kotlin",
        delimiter = ';'
    )
    fun `phone digits can build words`(digits: String, word: String) {
        assertThat(
            letterCombinations(digits).firstOrNull { it == word }
        ).contains(
            word
        )
    }
}

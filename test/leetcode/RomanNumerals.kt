package leetcode

import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://leetcode.com/problems/integer-to-roman/
 *
 * 12. Integer to Roman
 * [Medium]
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol Value
 * I      1
 * V      5
 * X      10
 * L      50
 * C      100
 * D      500
 * M      1000
 *
 * For example, 2 is written as II in Roman numeral, just two one's added together.
 * 12 is written as XII, which is simply X + II.
 * The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 *
 * - I can be placed before V (5) and X (10) to make 4 and 9.
 * - X can be placed before L (50) and C (100) to make 40 and 90.
 * - C can be placed before D (500) and M (1000) to make 400 and 900.
 *
 * Given an integer, convert it to a roman numeral.
 *
 * Constraints:
 * - 1 <= num <= 3999
 */
@Suppress("CyclomaticComplexMethod")
fun Int.toRoman(): String {
    require(this in 1..3999)

    tailrec fun reverseRN(rn: String, n: Int): String = when {
        n >= 1000 -> reverseRN("M$rn", n - 1000)
        n >= 900 -> reverseRN("MC$rn", n - 900)
        n >= 500 -> reverseRN("D$rn", n - 500)
        n >= 400 -> reverseRN("DC$rn", n - 400)
        n >= 100 -> reverseRN("C$rn", n - 100)
        n >= 90 -> reverseRN("CX$rn", n - 90)
        n >= 50 -> reverseRN("L$rn", n - 50)
        n >= 40 -> reverseRN("LX$rn", n - 40)
        n >= 10 -> reverseRN("X$rn", n - 10)
        n >= 9 -> reverseRN("XI$rn", n - 9)
        n >= 5 -> reverseRN("V$rn", n - 5)
        n >= 4 -> reverseRN("VI$rn", n - 4)
        n >= 1 -> reverseRN("I$rn", n - 1)
        else -> rn
    }

    return reverseRN("", this).reversed()
}

/**
 * Unit tests
 */
class RomanNumeralsTest {

    @ParameterizedTest
    @CsvSource(
        "1, I",
        "2, II",
        "3, III",
        "4, IV",
        "5, V",
        "8, VIII",
        "9, IX",
        "10, X",
        "12, XII",
        "27, XXVII",
        "49, XLIX",
        "50, L",
        "169, CLXIX",
        "3999, MMMCMXCIX",
    )
    fun `quick check typical roman numerals`(n: Int, expectedRomanNumeral: String) {
        assertThat(
            n.toRoman()
        ).isEqualTo(
            expectedRomanNumeral
        )
    }

    @ParameterizedTest
    @CsvSource("1, I", "5, V", "10, X", "50, L", "100, C", "500, D", "1000, M")
    fun `roman digits correspond to correct values`(n: Int, expectedRomanNumeral: String) {
        assertThat(
            n.toRoman()
        ).isEqualTo(
            expectedRomanNumeral
        )
    }

    @ParameterizedTest
    @CsvSource("4, IV", "9, IX", "40, XL", "90, XC", "400, CD", "900, CM")
    fun `valid subtractions correspond to correct values`(n: Int, expectedRomanNumeral: String) {
        assertThat(
            n.toRoman()
        ).isEqualTo(
            expectedRomanNumeral
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, -17])
    fun `roman numerals do not support 0 and negative integers`(nonPositiveInteger: Int) {
        assertThrows<IllegalArgumentException> {
            nonPositiveInteger.toRoman()
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [4000, 4001, 17358])
    fun `roman numerals do not support integers larger than 3999`(largeInteger: Int) {
        assertThrows<IllegalArgumentException> {
            largeInteger.toRoman()
        }
    }
}

class RomanNumeralsPropertiesTest {

    private val romanSymbols1s = mapOf("I" to 1, "X" to 10, "C" to 100, "M" to 1000)
    private val romanSymbols5s = mapOf("V" to 5, "L" to 50, "D" to 500)
    private val romanSymbols = romanSymbols1s.plus(romanSymbols5s)

    @Property
    fun `comprises only roman symbols`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral)
            .`as`("$n as $romanNumeral must comprises only roman symbols")
            .matches(nonEmptyRomanSymbolsPattern)
    }

    private val nonEmptyRomanSymbolsPattern by lazy {
        romanSymbols.keys.joinToString(separator = "|", prefix = "(", postfix = ")+").toPattern()
    }

    @Property
    fun `comprises at most 3 of 'I', 'X', 'C', and 'M' in a row`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral.count(romanSymbols1s.keys.map { it.repeat(4) }))
            .`as`("$n as $romanNumeral must comprise %s most 3 times", romanSymbols1s.keys)
            .allMatch { it == 0 }
    }

    @Property
    fun `comprises at most 4 of 'I', 'X', 'C', and 'M'`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral.count(romanSymbols1s.keys))
            .`as`("$n as $romanNumeral must comprise %s most 3 times", romanSymbols1s.keys)
            .allMatch { it <= 4 }
    }

    @Property
    fun `comprises at most 1 of 'V', 'L', and 'D'`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral.count(romanSymbols5s.keys))
            .`as`("$n as $romanNumeral must comprise %s most 1 time", romanSymbols5s.keys)
            .allMatch { it <= 1 }
    }

    @Property
    fun `has no illegal subtractions like 'VX' or 'IL'`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral.count(illegalSubtractions))
            .`as`(
                "$n as $romanNumeral must not comprise any of illegal subtractions %s",
                illegalSubtractions
            )
            .allMatch { it == 0 }
    }

    private val illegalSubtractions = listOf("IL", "IC", "ID", "IM", "XD", "XM")

    @Property
    fun `has no ambiguous sequences like 'IVI' or 'CDC'`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeral.count(ambiguousSequences))
            .`as`(
                "$n as $romanNumeral must not comprise any ambiguities; i.e. %s",
                ambiguousSequences
            )
            .allMatch { it == 0 }
    }

    private val ambiguousSequences = listOf("IVI", "IXI", "XLX", "XCX", "CDC")

    @Property
    fun `symbol values decrease monotonically`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()
        val simplifiedRomanNumeral = simplified(romanNumeral)

        assertThat(simplifiedRomanNumeral.pairs())
            .`as`(
                "$n as $romanNumeral (as simplified $simplifiedRomanNumeral)" +
                    " must comprise of monotonically decreasing symbol values"
            )
            .allMatch { (d1, d2) -> romanSymbols.getValue(d1) >= romanSymbols.getValue(d2) }
    }

    @Property
    fun `converted back yields number`(@ForAll("1 to 3999") n: Int) {
        val romanNumeral = n.toRoman()

        assertThat(romanNumeralToInt(romanNumeral))
            .`as`("$n as $romanNumeral must convert back to $n")
            .isEqualTo(n)
    }

    private fun romanNumeralToInt(romanNumeral: String) =
        simplified(romanNumeral).map { romanSymbols.getValue(it.toString()) }.sum()

    private fun simplified(romanNumeral: String) =
        romanNumeral
            .replace("IV", "IIII")
            .replace("IX", "VIIII")
            .replace("XL", "XXXX")
            .replace("XC", "LXXXX")
            .replace("CD", "CCCC")
            .replace("CM", "DCCCC")

    @Provide("1 to 3999")
    @Suppress("unused")
    private fun numbers(): Arbitrary<Int> = Arbitraries.integers().between(1, 3999)
}

private fun String.count(string: String) = this.split(string).size - 1
private fun String.count(strings: Iterable<String>) = strings.map { count(it) }
private fun String.pairs() = windowed(2).map { it.take(1) to it.drop(1).take(1) }

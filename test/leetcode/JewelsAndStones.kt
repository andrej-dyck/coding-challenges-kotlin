package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/jewels-and-stones/
 *
 * 771. Jewels and Stones
 * [Easy]
 *
 * You're given strings jewels representing the types of stones that are jewels, and stones
 * representing the stones you have.
 * Each character in stones is a type of stone you have. You want to know how many of the
 * stones you have are also jewels.
 *
 * Letters are case sensitive, so "a" is considered a different type of stone from "A".
 *
 * Constraints:
 * - 1 <= jewels.length, stones.length <= 50
 * - jewels and stones consist of only English letters.
 * - All the characters of jewels are unique.
 */
fun numJewelsInStones(jewels: String, stones: String) =
    numJewelsInStones(jewels.toHashSet(), stones)

fun numJewelsInStones(jewels: Set<Char>, stones: CharSequence) =
    stones.count { it in jewels }

/**
 * Unit tests
 */
class JewelsAndStonesTest {

    @ParameterizedTest
    @CsvSource(
        "aA, aAAbbbb, 3",
        "z, ZZ, 0"
    )
    fun `number of jewels in stones`(jewels: String, stones: String, expectedNumber: Int) {
        assertThat(
            numJewelsInStones(jewels, stones)
        ).isEqualTo(
            expectedNumber
        )
    }
}

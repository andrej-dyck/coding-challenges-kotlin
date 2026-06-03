package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/two-sum/
 *
 * 1. Two Sum
 * [Easy]
 *
 * Given an array of integers nums and an integer target,
 * return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution,
 * and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 * Constraints:
 * - 2 <= nums.length <= 104
 * - -109 <= nums[i] <= 109
 * - -109 <= target <= 109
 * - Only one valid answer exists.
 */
fun twoSum(nums: Array<Int>, target: Int) =
    twoSumRec(nums.asSequence().withIndex(), target)
        ?: throw IllegalArgumentException("no valid answer exists")

@Suppress("NestedBlockDepth")
private tailrec fun twoSumRec(
    nums: Sequence<IndexedValue<Int>>,
    target: Int,
    seenValues: Map<Int, Int> = emptyMap()
): Pair<Int, Int>? {
    val (head, tails) = nums.firstOrNull() to nums.drop(1)

    return if (head == null) null
    else when (val otherIndex = seenValues[target - head.value]) {
        null -> twoSumRec(tails, target, seenValues = seenValues + (head.value to head.index))
        else -> otherIndex to head.index
    }
}

/**
 * Unit Tests
 */
class TwoSumTest {

    @ParameterizedTest
    @CsvSource(
        "[2,7,11,15]; 9; (0,1)",
        "[3,2,4]; 6; (1,2)",
        "[3,3]; 6; (0,1)",
        delimiter = ';'
    )
    fun `indices of the numbers in nums with nums_i1 + nums_i2 = target`(
        @ConvertWith(IntArrayArg::class) nums: Array<Int>,
        target: Int,
        expectedIndices: String
    ) {
        Assertions.assertThat(
            twoSum(nums, target)
        ).isEqualTo(
            expectedIndices.toIntPair()
        )
    }
}

private fun String.toIntPair() =
    removeSurrounding("(", ")")
        .split(",")
        .let { it[0].toInt() to it[1].toInt() }

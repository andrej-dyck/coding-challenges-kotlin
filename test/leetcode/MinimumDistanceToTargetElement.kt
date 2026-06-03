package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.abs

/**
 * https://leetcode.com/problems/minimum-distance-to-the-target-element/
 *
 * 1848. Minimum Distance to the Target Element
 * [Easy]
 *
 * Given an integer array nums (0-indexed) and two integers target and start,
 * find an index i such that nums[i] == target and abs(i - start) is minimized.
 * Note that abs(x) is the absolute value of x.
 *
 * Return abs(i - start).
 *
 * It is guaranteed that target exists in nums.
 *
 * Constraints:
 * - 1 <= nums.length <= 1000
 * - 1 <= nums[i] <= 10^4
 * - 0 <= start < nums.length
 * - target is in nums.
 */
fun <T : Comparable<T>> minDistanceToTarget(
    elements: Array<T>,
    targetElement: T,
    startIndex: Int
) =
    elements
        .asSequence()
        .mapIndexed { i, e -> e to i }
        .filter { it.first == targetElement }
        .map { startIndex.distanceTo(it.second) }
        .minOrNull()

private fun Int.distanceTo(other: Int) = abs(other - this)

/**
 * Unit tests
 */
class MinimumDistanceToTargetElement {

    @ParameterizedTest
    @CsvSource(
        "[1,2,3,4,5]; 5; 3; 1",
        "[1]; 1; 0; 0",
        "[1,1,1,1,1,1,1,1,1,1]; 1; 0; 0",
        delimiter = ';'
    )
    fun `nums can be made non-decreasing by modifying at most one element`(
        @ConvertWith(IntArrayArg::class) nums: Array<Int>,
        targetElement: Int,
        startIndex: Int,
        expectedMinDistance: Int
    ) {
        Assertions.assertThat(
            minDistanceToTarget(nums, targetElement, startIndex)
        ).isEqualTo(
            expectedMinDistance
        )
    }
}

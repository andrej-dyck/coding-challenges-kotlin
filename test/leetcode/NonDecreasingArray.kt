package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/non-decreasing-array/
 *
 * 665. Non-decreasing Array
 * [Medium]
 *
 * Given an array nums with n integers, your task is to check if it could
 * become non-decreasing by modifying at most one element.
 *
 * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds
 * for every i (0-based) such that (0 <= i <= n - 2).
 *
 * Constraints:
 * - n == nums.length
 * - 1 <= n <= 10^4
 * - -10^5 <= nums[i] <= 10^5
 */
fun canBeMadeNonDecreasing(nums: Array<Int>): Boolean =
    nums.asSequence()
        .windowed(2)
//      .count { (n1, n2) -> n1 > n2 } <= 1 // count all is not optimal for this, we just need to count to 2
        .countUpToMax(2) { (n1, n2) -> n1 > n2 } <= 1

fun <T> Sequence<T>.countUpToMax(max: Int, predicate: (T) -> Boolean) =
    filter(predicate)
        .zip((1..max).asSequence())
        .count()

/**
 * Unit tests
 */
class NonDecreasingArrayTest {

    @ParameterizedTest
    @CsvSource(
        "[]; true",
        "[1]; true",
        "[2, 1]; true",
        "[4,2,3]; true",
        "[4,2,1]; false",
        "[4,2,3,4,7,10]; true",
        "[4,2,3,5,4,10]; false",
        delimiter = ';'
    )
    fun `nums can be made non-decreasing by modifying at most one element`(
        @ConvertWith(IntArrayArg::class) nums: Array<Int>,
        isPossible: Boolean
    ) {
        assertThat(
            canBeMadeNonDecreasing(nums)
        ).isEqualTo(
            isPossible
        )
    }
}

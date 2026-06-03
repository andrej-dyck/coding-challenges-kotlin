package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.sign

/**
 * https://leetcode.com/problems/sign-of-the-product-of-an-array/
 *
 * 1822. Sign of the Product of an Array
 * [Easy]
 *
 * There is a function signFunc(x) that returns:
 * - 1 if x is positive.
 * - -1 if x is negative.
 * - 0 if x is equal to 0.
 *
 * You are given an integer array nums. Let product be the product of all values in the array nums.
 * Return signFunc(product).
 *
 * Constraints:
 * - 1 <= nums.length <= 1000
 * - -100 <= nums[i] <= 100
 */
fun signOfArray(nums: Array<Int>) =
    nums.map(Int::sign).fold(1, Int::times)

/**
 * Unit Tests
 */
class SignOfTheProductOfAnArrayTest {

    @ParameterizedTest
    @CsvSource(
        "[0]; 0",
        "[1]; 1",
        "[-1]; -1",
        "[-1,-2,-3,-4,3,2,1]; 1",
        "[1,5,0,2,-3]; 0",
        "[-1,1,-1,1,-1]; -1",
        delimiter = ';'
    )
    fun `sign of the product of numbers`(
        @ConvertWith(IntArrayArg::class) numbers: Array<Int>,
        expectedSign: Int
    ) {
        Assertions.assertThat(
            signOfArray(numbers)
        ).isEqualTo(
            expectedSign
        )
    }
}

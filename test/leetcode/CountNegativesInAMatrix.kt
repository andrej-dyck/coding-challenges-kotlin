package leetcode

import lib.IntMatrixArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/
 *
 * 1351. Count Negative Numbers in a Sorted Matrix
 * [Easy]
 *
 * Given a m x n matrix grid which is sorted in non-increasing order both row-wise and column-wise,
 * return the number of negative numbers in grid.
 *
 * Constraints
 * - m == grid.length
 * - n == grid[i].length
 * - 1 <= m, n <= 100
 * - -100 <= grid[i][j] <= 100
 */
fun countNegatives(grid: Array<Array<Int>>) =
    grid.flatten().count { it < 0 }

/**
 * Unit tests
 */
class CountNegativesTest {

    @ParameterizedTest
    @CsvSource(
        "[[]]; 0",
        "[[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]; 8",
        "[[3,2],[1,0]]; 0",
        "[[1,-1],[-1,-1]]; 3",
        "[[-1]]; 1",
        delimiter = ';'
    )
    fun `count of negative numbers in matrix`(
        @ConvertWith(IntMatrixArg::class) grid: Array<Array<Int>>,
        expectedCount: Int
    ) {
        assertThat(
            countNegatives(grid)
        ).isEqualTo(
            expectedCount
        )
    }
}

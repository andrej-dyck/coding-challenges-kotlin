package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/height-checker/
 *
 * 1051. Height Checker
 * [Easy]
 *
 * Students are asked to stand in non-decreasing order of heights for an annual photo.
 *
 * Return the minimum number of students that must move in order for all students to be
 * standing in non-decreasing order of height.
 *
 * Notice that when a group of students is selected they can reorder in any possible way
 * between themselves and the non selected students remain on their seats.
 *
 * Constraints:
 * - 1 <= heights.length <= 100
 * - 1 <= heights[i] <= 100
 */
fun numberOfStudentsToMove(heights: Array<Int>): Int =
    heights
        .zip(heights.sortedArray())
        .count { it.first != it.second }

/**
 * Unit tests
 */
class HeightCheckerTest {

    @ParameterizedTest
    @CsvSource(
        "[1,2,3,4,5]; 0",
        "[1,1,4,2,1,3]; 3",
        "[5,1,2,3,4]; 5",
        delimiter = ';'
    )
    fun `number of students to move to achieve asc order`(
        @ConvertWith(IntArrayArg::class) heights: Array<Int>,
        expectedNumber: Int
    ) {
        assertThat(
            numberOfStudentsToMove(heights)
        ).isEqualTo(
            expectedNumber
        )
    }
}

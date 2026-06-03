package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/first-bad-version/
 *
 * 278. First Bad Version
 * [Easy]
 *
 * ou are a product manager and currently leading a team to develop a new product.
 * Unfortunately, the latest version of your product fails the quality check.
 * Since each version is developed based on the previous version,
 * all the versions after a bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first
 * bad one, which causes all the following ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which returns whether version is bad.
 * Implement a function to find the first bad version.
 * You should minimize the number of calls to the API.
 *
 * Constraints:
 * - 1 <= bad <= n <= 2^31 - 1
 */
fun firstBadVersion(n: Int, isBadVersion: (v: Int) -> Boolean) =
    binarySearch(1..n, isBadVersion)

tailrec fun binarySearch(range: IntRange, isBefore: (mid: Int) -> Boolean): Int =
    if (range.count() == 1) range.first
    else binarySearch(
        range.upToOrOtherwiseAfter(range.mid()) { isBefore(it) },
        isBefore
    )

fun IntRange.mid() = first + (last - first) / 2

fun IntRange.upToOrOtherwiseAfter(n: Int, takeUntil: (n: Int) -> Boolean) =
    if (takeUntil(n)) first..n else (n + 1)..last

/**
 * Unit tests
 */
class FirstBadVersionTest {

    @ParameterizedTest
    @CsvSource(
        "5, 4",
        "10, 4",
        "1, 1"
    )
    fun `first bad version of n versions`(numberOfVersions: Int, badVersion: Int) {
        val fakeApi = { v: Int -> v >= badVersion }

        assertThat(
            firstBadVersion(numberOfVersions, fakeApi)
        ).isEqualTo(
            badVersion
        )
    }
}

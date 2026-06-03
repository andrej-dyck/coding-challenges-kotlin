package leetcode

import lib.IntArrayArg
import lib.headTails
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/assign-cookies/
 *
 * 455. Assign Cookies
 * [Easy]
 *
 * Assume you are an awesome parent and want to give your children some cookies.
 * But, you should give each child at most one cookie.
 *
 * Each child i has a greed factor g[i], which is the minimum size of a cookie that the child
 * will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign
 * the cookie j to the child i, and the child i will be content. Your goal is to maximize
 * the number of your content children and output the maximum number.
 *
 * Constraints:
 * - 1 <= g.length <= 3 * 10^4
 * - 0 <= s.length <= 3 * 10^4
 * - 1 <= g[i], s[j] <= 2^31 - 1
 */
fun findContentChildren(childrenGreeds: Array<Int>, cookieSizes: Array<Int>) =
    childrenGreeds.asSequence().sorted()
        .zipIf(cookieSizes.asSequence().sorted()) { g, s -> s >= g }
        .count()

/**
 * zipIf - integrates sequence with only those items from the other sequence which
 * satisfy the predicate
 */
fun <T, R> Sequence<T>.zipIf(
    other: Sequence<R>,
    predicate: (T, R) -> Boolean
): Sequence<Pair<T, R>> {

    tailrec fun recZipIf(
        s1: Sequence<T>,
        s2: Sequence<R>,
        pairs: Sequence<Pair<T, R>> = emptySequence()
    ): Sequence<Pair<T, R>> {
        val (f1, rs1) = s1.headTails()
        val (f2, rs2) = s2.dropWhile { f1 != null && !predicate(f1, it) }.headTails()

        return when {
            f1 == null || f2 == null -> pairs
            else -> recZipIf(rs1, rs2, pairs + (f1 to f2))
        }
    }

    return recZipIf(this, other)
}

//fun <T, R> Sequence<T>.zipIf(other: Sequence<R>, predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
//    val thisIterator = iterator()
//    val otherIterator = other.iterator()
//
//    return sequence {
//        while (thisIterator.hasNext()) {
//            val thisNext = thisIterator.next()
//
//            while (otherIterator.hasNext()) {
//                val otherNext = otherIterator.next()
//
//                if (predicate(thisNext, otherNext)) {
//                    yield(thisNext to otherNext)
//                    break
//                }
//            }
//        }
//    }
//}

/**
 * Unit tests
 */
class AssignCookiesTest {

    @ParameterizedTest
    @CsvSource(
        "[1,2,3]; [1,1]; 1",
        "[1,2]; [1,2,3]; 2",
        "[4,1,3,2,1,1,2,2]; [1,1,1,1]; 3",
        "[4,1,3,2,1,1,2,2]; [2,2,2,2]; 4",
        "[1,2,3,3]; [1,1,1,1,3,3,3]; 4",
        "[2,2,2,5]; [1,1,1,4]; 1",
        delimiter = ';'
    )
    fun `maximize the number of your content children`(
        @ConvertWith(IntArrayArg::class) childrenGreed: Array<Int>,
        @ConvertWith(IntArrayArg::class) cookieSizes: Array<Int>,
        expectedNumberOfContentChildren: Int
    ) {
        assertThat(
            findContentChildren(childrenGreed, cookieSizes)
        ).isEqualTo(
            expectedNumberOfContentChildren
        )
    }
}

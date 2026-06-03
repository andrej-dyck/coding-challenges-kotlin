package leetcode

import lib.IntMatrixArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/count-servers-that-communicate/
 *
 * 1267. Count Servers that Communicate
 * [Medium]
 *
 * You are given a map of a server center, represented as a m * n integer matrix grid,
 * where 1 means that on that cell there is a server and 0 means that it is no server.
 * Two servers are said to communicate if they are on the same row or on the same column.
 *
 * Return the number of servers that communicate with any other server.
 *
 * Constraints:
 * - m == grid.length
 * - n == grid[i].length
 * - 1 <= m <= 250
 * - 1 <= n <= 250
 * - grid[i][j] == 0 or 1
 */
fun communicatingServers(grid: Array<Array<Int>>): Int =
    with(grid.coordinates { it > 0 }) {
        size - isolatedServers(this)
    }

fun <T> Array<Array<T>>.coordinates(predicate: (T) -> Boolean) =
    flatMapIndexed { rowIndex, r ->
        r.mapIndexed { colIndex, c -> (rowIndex to colIndex).takeIf { predicate(c) } }
            .filterNotNull()
    }.toHashSet()

fun isolatedServers(serverCoordinates: HashSet<Pair<Int, Int>>): Int {
    val occurrencesRows = serverCoordinates.groupingBy { it.first }.eachCount()
    val occurrencesCols = serverCoordinates.groupingBy { it.second }.eachCount()

    return serverCoordinates.count { c ->
        occurrencesRows[c.first] == 1 && occurrencesCols[c.second] == 1
    }
}

/**
 * Unit tests
 */
class CommunicatingServersTest {

    @ParameterizedTest
    @CsvSource(
        "[[1,0],[0,1]]; 0",
        "[[1,0],[1,1]]; 3",
        "[[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]; 4",
        "[[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]; 0",
        "[[1,1,1,1],[1,1,1,1],[1,1,1,1],[1,1,1,1]]; 16",
        delimiter = ';'
    )
    fun `number of servers that communicate in given grid`(
        @ConvertWith(IntMatrixArg::class) grid: Array<Array<Int>>,
        expectedCount: Int
    ) {
        Assertions.assertThat(
            communicatingServers(grid)
        ).isEqualTo(
            expectedCount
        )

    }
}

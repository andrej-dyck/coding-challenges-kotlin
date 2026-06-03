package leetcode

import lib.IntArrayArg
import misc.chose
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/prison-cells-after-n-days/
 *
 * 957. Prison Cells After N Days
 * [Medium]
 *
 * There are 8 prison cells in a row, and each cell is either occupied or vacant.
 *
 * Each day, whether the cell is occupied or vacant changes according to the following rules:
 * - If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
 * - Otherwise, it becomes vacant.
 *
 * (Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.)
 *
 * We describe the current state of the prison in the following way:
 * cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.
 *
 * Given the initial state of the prison, return the state of the prison after N days
 * (and N such changes described above.)
 *
 * Note:
 * - cells.length == 8
 * - cells[i] is in {0, 1}
 * - 1 <= N <= 10^9
 */

fun prisonAfterNDays(initialCells: Array<Int>, n: Long): Array<Int> {
    tailrec fun cycleStates(cells: Array<Int>, n: Long): Array<Int> =
        if (n <= 0) cells
        else cycleStates(prisonNextDayState(cells), n - 1)

    val cyclesNeeded = (n - 1) % maxPrisonCycle(initialCells.size) + 1
    return cycleStates(initialCells, cyclesNeeded)
}

/*
 * for 8 cells, this gives us a max cycle of 14.
 * note that we could have a cycles of length 1 or 7 as wells
 */
private fun maxPrisonCycle(numberOfCells: Int) =
    (numberOfCells - 2) /* the outer cells are never occupied */
        .chose(2) /* as we have only occupied or vacant */
        .minus(1) /* since the outer cells become and/or stay vacant,
                           their adjacent cells have 1 less possibility to flip */

private fun prisonNextDayState(cells: Array<Int>) =
    cells.mapIndexed { i, _ ->
        if (cells.leftOf(i) == cells.rightOf(i)) 1
        else 0
    }.toTypedArray()

private fun <T> Array<T>.leftOf(i: Int): T? = getOrNull(i - 1)
private fun <T> Array<T>.rightOf(i: Int): T? = getOrNull(i + 1)

/**
 * Unit tests
 */
class PrisonCellsAfterNDaysTest {

    @ParameterizedTest
    @CsvSource(
        "[0,1,0,1,1,0,0,1]; [0,1,1,0,0,0,0,0]",
        "[0,1,1,0,0,0,0,0]; [0,0,0,0,1,1,1,0]",
        "[0,0,0,0,1,1,1,0]; [0,1,1,0,0,1,0,0]",
        "[0,1,1,0,0,1,0,0]; [0,0,0,0,0,1,0,0]",
        "[0,0,0,0,0,1,0,0]; [0,1,1,1,0,1,0,0]",
        "[0,1,1,1,0,1,0,0]; [0,0,1,0,1,1,0,0]",
        "[0,0,1,0,1,1,0,0]; [0,0,1,1,0,0,0,0]",
        delimiter = ';'
    )
    fun `next day's cell becomes occupied iff both adjacent cells are either vacant or occupied`(
        @ConvertWith(IntArrayArg::class) initialCells: Array<Int>,
        @ConvertWith(IntArrayArg::class) expectedCellsNextDay: Array<Int>,
    ) {
        assertThat(
            prisonNextDayState(initialCells)
        ).containsExactly(
            *expectedCellsNextDay
        )
    }

    @ParameterizedTest
    @CsvSource(
        "[0,1,0,1,1,0,0,1]; 7; [0,0,1,1,0,0,0,0]",
        "[1,0,0,1,0,0,1,0]; 1000000000; [0,0,1,1,1,1,1,0]",
        delimiter = ';'
    )
    fun `returns the state of the prison after n days`(
        @ConvertWith(IntArrayArg::class) initialCells: Array<Int>,
        n: Long,
        @ConvertWith(IntArrayArg::class) expectedStateAfterNDays: Array<Int>,
    ) {
        assertThat(
            prisonAfterNDays(initialCells, n)
        ).containsExactly(
            *expectedStateAfterNDays
        )
    }
}

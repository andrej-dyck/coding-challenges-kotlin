package leetcode

import lib.StringArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/word-search/
 *
 * 79. Word Search
 * [Medium]
 *
 * Given an m x n board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells,
 * where "adjacent" cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Constraints:
 * - m == board.length
 * - n = board[i].length
 * - 1 <= m, n <= 200
 * - 1 <= word.length <= 10^3
 * - board and word consists only of lowercase and uppercase English letters.
 */
typealias Board = Array<String>
typealias Coordinates = Sequence<XY>
typealias Path = Coordinates

fun findWordsOnBoard(board: Board, words: List<String>) =
    words.filter { boardContainsWord(board, it) }

fun boardContainsWord(board: Board, word: String) = board.findPath(word).any() // for leetcode

fun Board.findPath(word: String, xys: Coordinates = xys(), path: Path = emptySequence()): Path =
    if (word.isEmpty()) path
    else xys
        .filter { word.first() == maybeValue(it) && it !in path }
        .map { findPath(word.drop(1), it.neighbors(), path + it) }
        .find { it.any() }
        .orEmpty()

private fun Board.xys() =
    asSequence().flatMapIndexed { y, r -> r.mapIndexed { x, _ -> XY(x, y) } }

private fun Board.maybeValue(xy: XY) = getOrNull(xy.y)?.getOrNull(xy.x)

data class XY(val x: Int, val y: Int) {

    fun neighbors() = sequenceOf(right(), down(), up(), left())

    fun up() = copy(y = y - 1)
    fun right() = copy(x = x + 1)
    fun down() = copy(y = y + 1)
    fun left() = copy(x = x - 1)
}

/**
 * Unit tests
 */
class WordsInsideARectangleTest {

    @ParameterizedTest
    @CsvSource(
        "[KOTE, NULE, AFIN]; [Kotlin, fun, file, line, null]; [KOTLIN, FUN, FILE, LINE]",
        "[ABCE, SFCS, ADEE]; [ABCCED, SEE, ABCB]; [ABCCED, SEE]", // ABCB can only be found by visiting a cell twice
        delimiter = ';'
    )
    fun `finds words on board by finding a path of adjacent cells starting anywhere`(
        @ConvertWith(StringArrayArg::class) board: Array<String>,
        @ConvertWith(StringArrayArg::class) words: Array<String>,
        @ConvertWith(StringArrayArg::class) expectedWords: Array<String>
    ) {
        assertThat(
            findWordsOnBoard(board, words.map { it.uppercase() })
        ).containsExactlyElementsOf(
            expectedWords.toList()
        )
    }
}

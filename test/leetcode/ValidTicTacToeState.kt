package leetcode

import lib.formattedString
import lib.toArray
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://leetcode.com/problems/valid-tic-tac-toe-state/
 *
 * 794. Valid Tic-Tac-Toe State
 * [Medium]
 *
 * A Tic-Tac-Toe board is given as a string array board.
 * Return True if and only if it is possible to reach this board position
 * during the course of a valid tic-tac-toe game.
 *
 * The board is a 3 x 3 array, and consists of characters " ", "X", and "O".
 * The " " character represents an empty square.
 *
 * Here are the rules of Tic-Tac-Toe:
 * - Players take turns placing characters into empty squares (" ").
 * - The first player always places "X" characters, while the second player always places "O" characters.
 * - "X" and "O" characters are always placed into empty squares, never filled ones.
 * - The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * - The game also ends if all squares are non-empty.
 * - No more moves can be played if the game is over.
 *
 * Note:
 * - board is a length-3 array of strings, where each string board[i] has length 3.
 * - Each board[i][j] is a character in the set {" ", "X", "O"}.
 */
fun validTicTacToe(board: Array<String>) = TicTacToeBoard(board).isInValidState

class TicTacToeBoard(private val board: Array<String>) {

    val isInValidState by lazy {
        when {
            xWon -> !oWon && xs == os + 1
            oWon -> !xWon && xs == os
            else -> xs == os || xs == os + 1
        }
    }

    val xWon by lazy { isWinner('X') }
    val oWon by lazy { isWinner('O') }

    private val xs by lazy { board.joinToString().count { it == 'X' } }
    private val os by lazy { board.joinToString().count { it == 'O' } }

    private fun isWinner(c: Char): Boolean =
        anyRowContainsOnly(c)
            || anyColumnContainsOnly(c)
            || anyDiagonalContainsOnly(c)

    private fun anyRowContainsOnly(c: Char) =
        board.any { it == "$c".repeat(3) }

    private fun anyColumnContainsOnly(c: Char) =
        (0..2).any { colIndex -> board.all { it[colIndex] == c } }

    private fun anyDiagonalContainsOnly(c: Char) =
        (0..2).all { board[it][it] == c } || (0..2).all { board[it][2 - it] == c }

    init {
        require(
            board.size == 3 && board.all { s ->
                s.length == 3 && s.all { c -> c in " XO" }
            }
        ) { "invalid board representation: ${board.formattedString()}" }
    }
}

/**
 * Unit tests
 */
class ValidTicTacToeStateTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"   \", \"   \", \"   \"]",
            "[\"   \", \" XO\", \"   \"]",
            "[\" XO\", \" XO\", \"   \"]",
            "[\" X \", \" XO\", \" O \"]",
            "[\"XOX\", \"O O\", \"XOX\"]",
        ]
    )
    fun `valid board when same count of Xs and Os (and no winner)`(board: String) =
        assertIsValid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"   \", \" X \", \"   \"]",
            "[\" X \", \" XO\", \"   \"]",
            "[\" XO\", \" XO\", \" X \"]",
            "[\"OXO\", \"OXX\", \"XOX\"]",
        ]
    )
    fun `valid board when count of Xs is 1 more than Os (and no winner)`(board: String) =
        assertIsValid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"XXX\", \"   \", \"O O\"]",
            "[\" X \", \" XO\", \" XO\"]",
            "[\"XO \", \" XO\", \"  X\"]",
        ]
    )
    fun `valid board when X won and count of Os is 1 less (and O did not win)`(board: String) =
        assertIsValid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"OOO\", \" X \", \"X X\"]",
            "[\" XO\", \" XO\", \"X O\"]",
            "[\"OX \", \" OX\", \"O X\"]",
        ]
    )
    fun `valid board when O won and count of Xs is equal (and X did not win)`(board: String) =
        assertIsValid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"O  \", \"   \", \"   \"]",
            "[\"OO \", \"XXX\", \" OO\"]",
            "[\"OOO\", \" X \", \"   \"]",
        ]
    )
    fun `invalid board when count of Os is more than Xs`(board: String) =
        assertIsInvalid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"OOO\", \"XXX\", \"   \"]",
            "[\"XO \", \"XO \", \"XO \"]",
        ]
    )
    fun `invalid board when two winners exist`(board: String) =
        assertIsInvalid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"XXX\", \" O \", \"O O\"]",
        ]
    )
    fun `invalid board when X won and count of Os is equal`(board: String) =
        assertIsInvalid(board)

    @ParameterizedTest
    @ValueSource(
        strings = [
            "[\"OOO\", \"XX \", \"X X\"]",
        ]
    )
    fun `invalid board when O won and count of Xs is 1 more`(board: String) =
        assertIsInvalid(board)

    private fun assertIsValid(board: String) {
        assertThat(
            validTicTacToe(board.toArray { removeSurrounding("\"") })
        ).`as`("$board is valid").isTrue
    }

    private fun assertIsInvalid(board: String) {
        assertThat(
            validTicTacToe(board.toArray { removeSurrounding("\"") })
        ).`as`("$board is invalid").isFalse
    }
}

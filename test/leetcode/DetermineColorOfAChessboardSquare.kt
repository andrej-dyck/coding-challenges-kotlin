package leetcode

import leetcode.SquareColor.BLACK
import leetcode.SquareColor.WHITE
import lib.isEven
import lib.require
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * https://leetcode.com/problems/determine-color-of-a-chessboard-square/description/
 *
 * 1812. Determine Color of a Chessboard Square
 * [Easy]
 *
 * You are given coordinates, a string that represents the coordinates of a square of the chessboard.
 * Below is a chessboard for your reference.
 *
 * 8 w b w b w b w b
 * 7 b w b w b w b w
 * 6 w b w b w b w b
 * 5 b w b w b w b w
 * 4 w b w b w b w b
 * 3 b w b w b w b w
 * 2 w b w b w b w b
 * 1 b w b w b w b w
 *   A B C D E F G H
 *
 * Return true if the square is white, and false if the square is black.
 *
 * The coordinate will always represent a valid chessboard square.
 * The coordinate will always have the letter first, and the number second.
 *
 * Constraints:
 * - coordinates.length == 2
 * - 'a' <= coordinates[0] <= 'h'
 * - '1' <= coordinates[1] <= '8'
 */
fun squareIsWhite(coordinate: String) = squareColor(coordinate) == WHITE
fun squareIsBlack(coordinate: String) = squareColor(coordinate) == BLACK

enum class SquareColor { WHITE, BLACK }

fun squareColor(coordinate: String) =
    if (coordinatePair(coordinate).isEven()) BLACK else WHITE

private fun coordinatePair(coordinate: String) =
    coordinate
        .require { it.length == 2 }
        .let { coordinateValue(it.first()) to coordinateValue(it.last()) }

private fun coordinateValue(c: Char) = when (c) {
    in 'a'..'h' -> c - 'a' + 1
    in '1'..'8' -> c.digitToInt()
    else -> throw IllegalArgumentException()
}

private fun Pair<Int, Int>.isEven() = (first + second).isEven()

/**
 * Unit Tests
 */
class DetermineColorOfAChessboardSquareTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a1", "c1", "e1", "g1",
            "b2", "d2", "f2", "h2",
            "a3", "c3", "e3", "g3",
            "b4", "d4", "f4", "h4",
            "a5", "c5", "e5", "g5",
            "b6", "d6", "f6", "h6",
            "a7", "c7", "e7", "g7",
            "b8", "d8", "f8", "h8"
        ]
    )
    fun `black squares are`(coordinate: String) {
        assertThat(
            squareIsBlack(coordinate)
        ).`as`("$coordinate must be black square").isTrue
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "b1", "d1", "f1", "h1",
            "a2", "c2", "e2", "g2",
            "b3", "d3", "f3", "h3",
            "a4", "c4", "e4", "g4",
            "b5", "d5", "f5", "h5",
            "a6", "c6", "e6", "g6",
            "b7", "d7", "f7", "h7",
            "a8", "c8", "e8", "g8"
        ]
    )
    fun `white squares are`(coordinate: String) {
        assertThat(
            squareIsWhite(coordinate)
        ).`as`("$coordinate must be white square").isTrue
    }
}

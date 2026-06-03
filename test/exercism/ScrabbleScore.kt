package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://exercism.io/tracks/kotlin/exercises/scrabble-score
 *
 * Scrabble Score
 * [Easy]
 *
 * Given a word, compute the Scrabble score for that word.
 */
object ScrabbleScore {

    fun scoreWord(word: String) = ScrabbleWord(word).score
}

class ScrabbleWord(word: String) {

    val score by lazy {
        word.sumOf { it.scrabblePoints }
    }
}

private val Char.scrabblePoints
    get() = when (uppercaseChar()) {
        'A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T' -> 1
        'D', 'G' -> 2
        'B', 'C', 'M', 'P' -> 3
        'F', 'H', 'V', 'W', 'Y' -> 4
        'K' -> 5
        'J', 'X' -> 8
        'Q', 'Z' -> 10
        else -> 0
    }

/**
 * Unit tests
 */
class ScrabbleScoreTest {

    @ParameterizedTest
    @CsvSource(
        "a, 1",
        "A, 1",
        "f, 4",
        "at, 2",
        "zoo, 12",
        "street, 6",
        "quirky, 22",
        "OxyphenButazone, 41",
        "pinata, 8",
        ", 0",
        "abcdefghijklmnopqrstuvwxyz, 87"
    )
    fun test(input: String?, expectedOutput: Int) {
        assertEquals(expectedOutput, ScrabbleScore.scoreWord(input.orEmpty()))
    }
}

package exercism

import lib.isDivisibleBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://exercism.io/tracks/kotlin/exercises/raindrops
 *
 * Raindrops
 * [Easy]
 *
 * Convert a number to a string, the content of which depends on the number's factors.
 */
class Raindrops(number: Int) {

    val asString by lazy {
        make(PLING, provided = number isDivisibleBy 3)
            .thenMake(PLANG, provided = number isDivisibleBy 5)
            .thenMake(PLONG, provided = number isDivisibleBy 7)
            .ifEmpty { number.toString() }
    }

    private fun make(sound: String, provided: Boolean) =
        NO_SOUND.thenMake(sound, provided)

    private fun String.thenMake(sound: String, provided: Boolean) =
        if (provided) this + sound else this

    companion object {
        fun convert(number: Int) = Raindrops(number).asString
    }
}

private const val PLING = "Pling"
private const val PLANG = "Plang"
private const val PLONG = "Plong"
private const val NO_SOUND = ""

/**
 * Unit tests
 */
class RaindropsTest {

    @ParameterizedTest
    @CsvSource(
        "1, 1",
        "3, Pling",
        "5, Plang",
        "7, Plong",
        "6, Pling",
        "8, 8",
        "9, Pling",
        "10,Plang",
        "14, Plong",
        "15, PlingPlang",
        "21, PlingPlong",
        "25, Plang",
        "27, Pling",
        "35, PlangPlong",
        "49, Plong",
        "52, 52",
        "105, PlingPlangPlong",
        "3125, Plang"
    )
    fun test(input: Int, expectedOutput: String) {
        assertEquals(expectedOutput, Raindrops.convert(input))
    }
}

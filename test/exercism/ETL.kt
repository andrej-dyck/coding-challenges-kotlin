package exercism

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * https://exercism.org/tracks/kotlin/exercises/ETL
 *
 * ETL
 * [Easy]
 *
 * Change the data format of letters and their point values in the game.
 */
object ETL {
    fun transform(source: Map<Int, Collection<Char>>): Map<Char, Int> =
        source.inverted().mapKeys { it.key.lowercaseChar() }
}

fun <TKey, TValue, TCollection : Collection<TValue>> Map<TKey, TCollection>.inverted(): Map<TValue, TKey> =
    asSequence().fold(emptyMap()) { acc, entry ->
        acc + entry.value.associateWith { entry.key }
    }

class ETLTest {

    @Test
    fun `full alphabet`() {
        Assertions.assertThat(
            ETL.transform(
                mapOf(
                    1 to listOf('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T'),
                    2 to listOf('D', 'G'),
                    3 to listOf('B', 'C', 'M', 'P'),
                    4 to listOf('F', 'H', 'V', 'W', 'Y'),
                    5 to listOf('K'),
                    8 to listOf('J', 'X'),
                    10 to listOf('Q', 'Z')

                )
            )
        ).isEqualTo(
            mapOf(
                'a' to 1, 'b' to 3, 'c' to 3, 'd' to 2, 'e' to 1,
                'f' to 4, 'g' to 2, 'h' to 4, 'i' to 1, 'j' to 8,
                'k' to 5, 'l' to 1, 'm' to 3, 'n' to 1, 'o' to 1,
                'p' to 3, 'q' to 10, 'r' to 1, 's' to 1, 't' to 1,
                'u' to 1, 'v' to 4, 'w' to 4, 'x' to 8, 'y' to 4,
                'z' to 10
            )
        )
    }
}

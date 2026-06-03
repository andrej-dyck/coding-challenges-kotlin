package misc

import lib.isANumber
import lib.isDivisibleBy
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Assumptions.assumingThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.abs

/**
 * https://en.wikipedia.org/wiki/Fizz_buzz
 *
 * Fizz Buzz
 */
interface FizzBuzz {

    operator fun invoke(number: Int): String
}

val fizzBuzz = FizzBuzzLookup()

/**
 * via Lookup Pattern
 */
class FizzBuzzLookup : FizzBuzz {

    override fun invoke(number: Int) =
        arrayOf(
            number.toString(), FIZZ, BUZZ, FIZZ_BUZZ
        )[
            lookupPattern[number.mod(15)]
        ]

    private val lookupPattern by lazy {
        arrayListOf(3, 0, 0, 1, 0, 2, 1, 0, 0, 1, 2, 0, 1, 0, 0)
    }
}

/**
 * via Sequence Cycles
 */
class FizzBuzzCycles : FizzBuzz {

    override fun invoke(number: Int) =
        fizzBuzzes().take(abs(number)).lastOrNull() ?: number.toString()

    private fun fizzBuzzes() =
        fizzes.zip(buzzes) { a, b -> a?.plus(b.orEmpty()) ?: b }

    private val fizzes by lazy {
        sequenceOf(null, null, "Fizz").cycle()
    }
    private val buzzes by lazy {
        sequenceOf(null, null, null, null, "Buzz").cycle()
    }
}

fun <T> Sequence<T>.cycle() = generateSequence(this) { this }.flatten()

/**
 * via Folding Functions
 */
class FizzBuzzFoldingFunctions : FizzBuzz {

    override fun invoke(number: Int) =
        listOf<(String) -> String>(
            { FIZZ.takeIf { number isDivisibleBy 3 } },
            { BUZZ.takeIf { number isDivisibleBy 5 } },
            { w -> number.toString().takeIf { w.isEmpty() } }
        ).fold("") { w, f ->
            w + f(w)
        }

    private fun String.takeIf(predicate: (String) -> Boolean) =
        if (predicate(this)) this else ""
}

/**
 * via Extension Functions
 */
class FizzBuzzExtensionFunctions : FizzBuzz {

    override fun invoke(number: Int): String {
        fun fizz() = FIZZ.takeIf { number isDivisibleBy 3 }
        fun String?.buzz() = if (number isDivisibleBy 5) orEmpty() + BUZZ else this

        return fizz().buzz() ?: number.toString()
    }
}

/**
 * Constants
 */
private const val FIZZ = "Fizz"
private const val BUZZ = "Buzz"
private const val FIZZ_BUZZ = "$FIZZ$BUZZ"

/**
 * Unit tests
 */
class FizzBuzzTest {

    @ParameterizedTest
    @CsvSource("1,1", "2,2", "3,Fizz", "4,4", "5,Buzz", "15,FizzBuzz", "30,FizzBuzz")
    fun `quick check for fizz buzz count`(n: Int, expectedWord: String) {
        assertThat(fizzBuzz(n))
            .isEqualTo(expectedWord)
    }
}

class FizzBuzzPropertiesTest {

    @Property
    fun `is either Fizz, Buzz, FizzBuzz, or the number as string`(@ForAll n: Int) {
        assertThat(fizzBuzz(n))
            .isIn(FIZZ, BUZZ, FIZZ_BUZZ, n.toString())
    }

    @Property
    fun `starts with Fizz for numbers divisible by 3`(@ForAll n: Int) {
        assumingThat(n != 0 && n isDivisibleBy 3) {
            assertThat(fizzBuzz(n)).startsWith(FIZZ)
        }
    }

    @Property
    fun `ends with Buzz for numbers divisible by 5`(@ForAll n: Int) {
        assumingThat(n != 0 && n isDivisibleBy 5) {
            assertThat(fizzBuzz(n)).endsWith(BUZZ)
        }
    }

    @Property
    fun `words that are numbers as string must be the input as string`(@ForAll n: Int) {
        val word = fizzBuzz(n)

        assumingThat(
            word.isANumber()
        ) {
            then(word).isEqualTo(n.toString())
                .`as`("FizzBuzz result must be the input's string representation")
        }
    }
}

package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.org/tracks/kotlin/exercises/say
 *
 * Say
 * [Medium]
 *
 * Given a number from 0 to 999,999,999,999, spell out that number in English.
 */
class NumberSpeller {

    fun say(number: Long): String {
        require(number in 0..999_999_999_999L)

        return number
            .chunksOfThousands()
            .joinToString(" ") { (chunk, magnitude) ->
                sayUpToThousand(chunk).ifNotEmpty { "$it $magnitude" }
            }.trim()
            .ifEmpty { "zero" }
    }

    private fun Long.chunksOfThousands() =
        toString()
            .chunkedRight(3) { it.toString().toInt() }
            .zip(listOf("", "thousand", "million", "billion"))
            .reversed()

    private fun sayUpToThousand(number: Int): String = when {
        number < 10 ->
            sayDigit(number)

        number in 10..19 ->
            sayTeens(number)

        number in 20..99 ->
            sayTens(number / 10) + sayDigit(number % 10).prependWith('-')

        number in 100..999 ->
            sayHundreds(number / 100) + sayUpToThousand(number % 100).prependWith(' ')

        else -> throw IllegalArgumentException("expected number in 0..999")
    }

    private fun sayDigit(digit: Int) = when (digit) {
        0 -> ""
        1 -> "one"
        2 -> "two"
        3 -> "three"
        4 -> "four"
        5 -> "five"
        6 -> "six"
        7 -> "seven"
        8 -> "eight"
        9 -> "nine"
        else -> throw IllegalArgumentException("expected number in 0..9")
    }

    private fun sayTeens(number: Int) = when (number) {
        10 -> sayTens(number)
        11 -> "eleven"
        12 -> "twelve"
        13 -> "thirteen"
        14 -> "fourteen"
        15 -> "fifteen"
        16 -> "sixteen"
        17 -> "seventeen"
        18 -> "eighteen"
        19 -> "nineteen"
        else -> throw IllegalArgumentException("expected number in 10..19")
    }

    private fun sayTens(number: Int) = when (number) {
        1 -> "ten"
        2 -> "twenty"
        3 -> "thirty"
        4 -> "forty"
        5 -> "fifty"
        6 -> "sixty"
        7 -> "seventy"
        8 -> "eighty"
        9 -> "ninety"
        else -> throw IllegalArgumentException("expected number to be 10, 20, 30, .., 90")
    }

    private fun sayHundreds(number: Int) =
        sayDigit(number) + " hundred"
}

private fun String.ifNotEmpty(use: (String) -> String) =
    if (isEmpty()) this else use(this)

private fun String.prependWith(char: Char) =
    ifNotEmpty { "$char$this" }

private fun <R> String.chunkedRight(size: Int, transform: (CharSequence) -> R) =
    reversed().chunked(size) { transform(it.reversed()) }

class NumberSpellerTest {

    @Test
    fun `0 as zero`() =
        0.shouldSoundLike("zero")

    @Test
    fun `1 as one`() =
        1.shouldSoundLike("one")

    @Test
    fun `14 as fourteen`() =
        14.shouldSoundLike("fourteen")

    @Test
    fun `20 as twenty`() =
        20.shouldSoundLike("twenty")

    @Test
    fun `22 as twenty-two`() =
        22.shouldSoundLike("twenty-two")

    @Test
    fun `100 as one hundred`() =
        100.shouldSoundLike("one hundred")

    @Test
    fun `123 as one hundred twenty-three`() =
        123.shouldSoundLike("one hundred twenty-three")

    @Test
    fun `1000 as one thousand`() =
        1000.shouldSoundLike("one thousand")

    @Test
    fun `100100 as one hundred thousand one hundred`() =
        100100.shouldSoundLike("one hundred thousand one hundred")

    @Test
    fun `1234 as one thousand two hundred thirty-four`() =
        1234.shouldSoundLike("one thousand two hundred thirty-four")

    @Test
    fun `1000000 as one million`() = 1000000.shouldSoundLike("one million")

    @Test
    fun `1002345 as one million two thousand three hundred forty-five`() =
        1002345.shouldSoundLike("one million two thousand three hundred forty-five")

    @Test
    fun `1000000000 as one billion`() =
        1000000000.shouldSoundLike("one billion")

    @Test
    fun `spell a big number`() =
        987654321123.shouldSoundLike(
            "nine hundred eighty-seven billion" +
                " six hundred fifty-four million" +
                " three hundred twenty-one thousand" +
                " one hundred twenty-three"
        )

    @Test
    fun `numbers below zero are out of range`() {
        assertThrows<IllegalArgumentException> {
            NumberSpeller().say(-1)
        }
    }

    @Test
    fun `numbers above 999,999,999,999 are out of range`() {
        assertThrows<IllegalArgumentException> {
            NumberSpeller().say(1000000000000)
        }
    }

}

private fun Long.shouldSoundLike(expected: String) =
    assertEquals(expected, NumberSpeller().say(this))

private fun Int.shouldSoundLike(expected: String) =
    this.toLong().shouldSoundLike(expected)







package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/acronym
 *
 * Acronym
 * [Easy]
 *
 * Convert a long phrase to its acronym.
 */
class Acronym(phrase: String) {

    val asString by lazy {
        firstLettersOfWords.map { it.uppercaseChar() }.joinToString(separator = "")
    }

    private val firstLettersOfWords by lazy {
        SimpleWords(phrase).map { it.first() }
    }

    companion object {
        fun generate(phrase: String) = Acronym(phrase).asString
    }
}

interface Words : Iterable<String>

class SimpleWords(phrase: String) : Words {

    private val words by lazy {
        phrase.replaceNonLettersWith(" ").trim().splitAtWhitespaces()
    }

    override fun iterator() = words.iterator()
}

private fun String.replaceNonLettersWith(replacement: String) =
    replace("""[^a-zA-Z']""".toRegex(), replacement)

private fun String.splitAtWhitespaces() =
    splitToSequence("""\s+""".toRegex())

/**
 * Unit Tests
 */
class AcronymTest {

    @Test
    fun fromTitleCasedPhrases() {
        val phrase = "Portable Network Graphics"
        val expected = "PNG"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromOtherTitleCasedPhrases() {
        val phrase = "Ruby on Rails"
        val expected = "ROR"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromPhrasesWithPunctuation() {
        val phrase = "First In, First Out"
        val expected = "FIFO"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromNonAcronymAllCapsWord() {
        val phrase = "GNU Image Manipulation Program"
        val expected = "GIMP"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromPhrasesWithPunctuationAndSentenceCasing() {
        val phrase = "Complementary metal-oxide semiconductor"
        val expected = "CMOS"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromVeryLongAbbreviation() {
        val phrase = "Rolling On The Floor Laughing So Hard That My Dogs Came Over And Licked Me"
        val expected = "ROTFLSHTMDCOALM"
        assertEquals(expected, Acronym.generate(phrase))
    }

    @Test
    fun fromConsecutiveDelimiters() {
        val phrase = "Something - I made up from thin air"
        val expected = "SIMUFTA"
        assertEquals(expected, Acronym.generate(phrase))
    }
}

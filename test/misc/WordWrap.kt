package misc

import net.jqwik.api.Arbitraries
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.arbitraries.IntegerArbitrary
import net.jqwik.api.arbitraries.ListArbitrary
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class WordWrap(private val column: Int) {

    fun wrap(text: String): List<String> =
        if (column <= 0) text.lines()
        else text.lines().flatMap(::wrapLine)

    private fun wrapLine(line: String) =
        line.words()
            .fold(WrappedLines(column)) { lines, word -> lines.take(word) }
}

private data class WrappedLines(
    private val column: Int,
    private val previousLines: Iterable<String> = emptyList(),
    private val currentLine: String = ""
) : Iterable<String> {

    fun take(word: String): WrappedLines =
        if (currentLine.length + word.length + 1 <= column)
            copy(currentLine = currentLine.withWord(word))
        else
            copy(previousLines = this, currentLine = word)

    override fun iterator() = iterator {
        yieldAll(previousLines)
        yieldNotNull(currentLine.trim().takeIf(String::isNotEmpty))
    }
}

fun String.words() = splitToSequence(' ')
fun String.withWord(word: String) = if (isEmpty()) word else "$this $word"

suspend fun <T> SequenceScope<T>.yieldNotNull(value: T?) = value?.apply { yield(this) }

/**
 * Unit Tests
 */
@Suppress("ClassName")
class WordWrapTest {

    @Test
    fun `a single line that is longer than column is wrapped at spaces`() {
        assertThat(
            WordWrap(12).wrap("I am a few words too long!")
        ).containsExactly(
            "I am a few",
            "words too",
            "long!"
        )
    }

    @Test
    fun `multiple lines are wrapped by wrapping each single line`() {
        assertThat(
            WordWrap(12).wrap(
                """
                    I am a few words too long!
                    I'm ok.
                    But this is definitely too long...!
                """.trimMargin()
            )
        ).containsExactly(
            "I am a few",
            "words too",
            "long!",
            "I'm ok.",
            "But this is",
            "definitely",
            "too long...!"
        )
    }

    @Test
    fun `no resulting line has trailing spaces`() {
        assertThat(
            WordWrap(4).wrap("abc abc abc       ")
        ).containsExactly(
            "abc",
            "abc",
            "abc"
        )
    }

    @Test
    fun `inner-line spaces are preserved`() {
        assertThat(
            WordWrap(5).wrap("a   b c")
        ).containsExactly(
            "a   b",
            "c"
        )
    }

    @Nested
    inner class `lines are not wrapped` {

        @ParameterizedTest
        @ValueSource(strings = ["", "word", "a word"])
        fun `when line is shorter or equal than column`(line: String) {
            assertThat(
                WordWrap(column = line.length).wrap(line)
            ).containsExactly(line)
        }

        @ParameterizedTest
        @ValueSource(strings = ["a", "word", "longword"])
        fun `when line comprises of is a single word that is longer than column`(line: String) {
            assertThat(
                WordWrap(column = line.length - 1).wrap(line)
            ).containsExactly(line)
        }

        @ParameterizedTest
        @ValueSource(strings = ["a", "word", "longword", "a long sentence of words"])
        fun `wrapped when column is 0`(line: String) {
            assertThat(
                WordWrap(column = 0).wrap(line)
            ).containsExactly(line)
        }

        @ParameterizedTest
        @ValueSource(strings = ["a", "word", "longword", "a long sentence of words"])
        fun `wrapped when column is negative`(line: String) {
            assertThat(
                WordWrap(column = -1).wrap(line)
            ).containsExactly(line)
        }
    }

    @Property
    fun `resulting lines comprising multiple words must be less than column`(
        @ForAll("englishWords") words: List<String>,
        @ForAll("columnValues") column: Int
    ) {
        assertThat(
            WordWrap(column).wrap(words.toLine())
        ).filteredOn {
            it.contains(' ')
        }.allMatch {
            it.length <= column
        }
    }

    @Property
    fun `resulting lines plus next line's first word must exceed column`(
        @ForAll("englishWords") words: List<String>,
        @ForAll("columnValues") column: Int
    ) {
        assertThat(
            WordWrap(column).wrap(words.toLine()).windowed(2)
        ).allMatch { (currentLine, nextLine) ->
            val firstWord = nextLine.splitToSequence(' ').first()

            currentLine.length + firstWord.length + 1 > column
        }
    }

    @Property
    fun `resulting lines joined together must be the original line`(
        @ForAll("englishWords") words: List<String>,
        @ForAll("columnValues") column: Int
    ) {
        val line = words.toLine()

        assertThat(
            WordWrap(column).wrap(line).toLine()
        ).isEqualTo(line)
    }

    @Provide
    @Suppress("CascadingCallWrapping")
    fun englishWords(): ListArbitrary<String> =
        Arbitraries
            .strings().alpha().ofMinLength(1).ofMaxLength(20)
            .list().ofMinSize(1).ofMaxSize(500)

    @Provide
    fun columnValues(): IntegerArbitrary =
        Arbitraries.integers().between(1, 80)

    private fun List<String>.toLine() = joinToString(" ")
}

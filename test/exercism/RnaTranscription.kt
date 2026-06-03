package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/rna-transcription
 *
 * RNA Transcription
 * [Easy]
 *
 * Given a DNA strand, return its RNA Complement Transcription.
 */
fun transcribeToRna(dna: String) =
    dna.map { transcribeToRna(it) }.joinToString(separator = "")

fun transcribeToRna(dnaNucleotide: Char) =
    when (dnaNucleotide) {
        'C' -> 'G'
        'G' -> 'C'
        'T' -> 'A'
        'A' -> 'U'
        else -> throw IllegalArgumentException("Unrecognized DNA nucleotide '$dnaNucleotide'")
    }

/**
 * Unit tests
 */
class RnaTranscriptionTest {

    @Test
    fun cytosineComplementIsGuanine() {
        assertEquals("G", transcribeToRna("C"))
    }

    @Test
    fun guanineComplementIsCytosine() {
        assertEquals("C", transcribeToRna("G"))
    }

    @Test
    fun thymineComplementIsAdenine() {
        assertEquals("A", transcribeToRna("T"))
    }

    @Test
    fun adenineComplementIsUracil() {
        assertEquals("U", transcribeToRna("A"))
    }

    @Test
    fun rnaTranscription() {
        assertEquals("UGCACCAGAAUU", transcribeToRna("ACGTGGTCTTAA"))
    }
}

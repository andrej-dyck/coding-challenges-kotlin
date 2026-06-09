package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://exercism.org/tracks/kotlin/exercises/protein-translation
 *
 * RNA Translation
 * [Easy]
 *
 * Translate RNA sequences into proteins.
 */
fun translate(rna: String?): List<String> =
    Rna(rna ?: "").toProteinSequence().map { it.englishName }.toList()

@JvmInline
value class Rna(private val rna: String) {

    fun toProteinSequence(): Sequence<Protein> =
        aminoAcids().map { it.toProtein() }.takeWhile { it != null }.filterNotNull()

    fun aminoAcids(): Sequence<AminoAcid> =
        rna.chunkedSequence(3) { AminoAcid(it.toString()) }
}

@JvmInline
value class AminoAcid(private val codon: String) {

    fun toProtein(): Protein? = when (codon) {
        "AUG" -> Protein.Methionine
        "UUU", "UUC" -> Protein.Phenylalanine
        "UUA", "UUG" -> Protein.Leucine
        "UCU", "UCC", "UCA", "UCG" -> Protein.Serine
        "UAU", "UAC" -> Protein.Tyrosine
        "UGU", "UGC" -> Protein.Cysteine
        "UGG" -> Protein.Tryptophan
        "UAA", "UAG", "UGA" -> null
        else -> throw IllegalArgumentException("Invalid codon")
    }
}

enum class Protein(val englishName: String) {
    Methionine("Methionine"),
    Phenylalanine("Phenylalanine"),
    Leucine("Leucine"),
    Serine("Serine"),
    Tyrosine("Tyrosine"),
    Cysteine("Cysteine"),
    Tryptophan("Tryptophan"),
}

class ProteinTranslationTest {

    @Test
    fun emptyRNAHasNoProteins() {
        assertEquals(emptyList<String>(), translate(null))
    }

    @Test
    fun `Sequence of two protein codons translates into proteins`() {
        assertEquals(listOf("Phenylalanine", "Phenylalanine"), translate("UUUUUU"))
    }

    @Test
    fun `Sequence of two different protein codons translates into proteins`() {
        assertEquals(listOf("Leucine", "Leucine"), translate("UUAUUG"))
    }

    @Test
    fun `Translate RNA strand into correct protein list`() {
        assertEquals(listOf("Methionine", "Phenylalanine", "Tryptophan"), translate("AUGUUUUGG"))
    }

    @Test
    fun `Translation stops if STOP codon at beginning of sequence`() {
        assertEquals(emptyList<String>(), translate("UAGUGG"))
    }

    @Test
    fun `Translation stops if STOP codon at end of three-codon sequence`() {
        assertEquals(listOf("Methionine", "Phenylalanine"), translate("AUGUUUUAA"))
    }

    @Test
    fun `Translation stops if STOP codon in middle of three-codon sequence`() {
        assertEquals(listOf("Tryptophan"), translate("UGGUAGUGG"))
    }

    @Test
    fun `Translation stops if STOP codon in middle of six-codon sequence`() {
        assertEquals(listOf("Tryptophan", "Cysteine", "Tyrosine"), translate("UGGUGUUAUUAAUGGUUU"))
    }

    @Test
    fun `Non-existing codon can't translate`() {
        val throwable = assertThrows<IllegalArgumentException> {
            translate("AAA")
        }
        assertEquals("Invalid codon", throwable.message)
    }

    @Test
    fun `Unknown amino acids, not part of a codon, can't translate`() {
        val throwable = assertThrows<IllegalArgumentException> {
            translate("XYZ")
        }
        assertEquals("Invalid codon", throwable.message)

    }

    @Test
    fun `Incomplete RNA sequence can't translate`() {
        val throwable = assertThrows<IllegalArgumentException> {
            translate("AUGU")
        }
        assertEquals("Invalid codon", throwable.message)
    }

    @Test
    fun `Incomplete RNA sequence can translate if valid until a STOP codon`() {
        assertEquals(listOf("Phenylalanine", "Phenylalanine"), translate("UUCUUCUAAUGGU"))
    }

    @ParameterizedTest
    @CsvSource(
        "Methionine <- AUG",
        "Phenylalanine <- UUU,UUC",
        "Leucine <- UUA,UUG",
        "Serine <- UCU,UCC,UCA,UCG",
        "Tyrosine <- UAU,UAC",
        "Cysteine <- UGU,UGC",
        "Tryptophan <- UGG",
        delimiterString = "<-"
    )
    fun `Protein codon translates into protein`(protein: String, codons: String) {
        codons.split(',').forEach { codon ->
            assertEquals(listOf(protein), translate(codon), "RNA sequence $codon translates into $protein")
        }
    }

}

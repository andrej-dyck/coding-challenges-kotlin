package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.io/tracks/kotlin/exercises/hamming
 *
 * Hamming
 * [Easy]
 *
 * Calculate the Hamming difference between two DNA strands.
 */
object Hamming {

    fun compute(dnaStrand1: String, dnaStrand2: String): Int {
        require(dnaStrand1.isDnaStrand() && dnaStrand2.isDnaStrand()) {
            "DNA strands must only comprise the following nucleotides: A, C, G, and T"
        }
        require(dnaStrand1.isHomologousTo(dnaStrand2)) {
            "left and right strands must be of equal length."
        }
        return dnaStrand1.zip(dnaStrand2).count { it.first != it.second }
    }

    private fun String.isDnaStrand() = all { it in NUCLEOTIDES }

    private fun String.isHomologousTo(other: String) = length == other.length

    private const val NUCLEOTIDES = "ACGT"
}

/**
 * Unit tests
 */
class HammingTest {

    @Test
    fun noDistanceBetweenEmptyStrands() {
        assertEquals(0, Hamming.compute("", ""))
    }

    @Test
    fun noDistanceBetweenShortIdenticalStrands() {
        assertEquals(0, Hamming.compute("A", "A"))
    }

    @Test
    fun noDistanceBetweenLongIdenticalStrands() {
        assertEquals(0, Hamming.compute("GGACTGA", "GGACTGA"))
    }

    @Test
    fun completeDistanceInSingleNucleotideStrand() {
        assertEquals(1, Hamming.compute("A", "G"))
    }

    @Test
    fun completeDistanceInSmallStrand() {
        assertEquals(2, Hamming.compute("AG", "CT"))
    }

    @Test
    fun smallDistanceInSmallStrand() {
        assertEquals(1, Hamming.compute("AT", "CT"))
    }

    @Test
    fun smallDistanceInMediumStrand() {
        assertEquals(1, Hamming.compute("GGACG", "GGTCG"))
    }

    @Test
    fun smallDistanceInLongStrand() {
        assertEquals(2, Hamming.compute("ACCAGGG", "ACTATGG"))
    }

    @Test
    fun nonUniqueCharacterInFirstStrand() {
        assertEquals(1, Hamming.compute("AAG", "AAA"))
    }

    @Test
    fun nonUniqueCharacterInSecondStrand() {
        assertEquals(1, Hamming.compute("AAA", "AAG"))
    }

    @Test
    fun sameNucleotidesInDifferentPositions() {
        assertEquals(2, Hamming.compute("TAG", "GAT"))
    }

    @Test
    fun largeDistanceInPermutedStrand() {
        assertEquals(4, Hamming.compute("GATACA", "GCATAA"))
    }

    @Test
    fun largeDistanceInOffByOneStrand() {
        assertEquals(9, Hamming.compute("GGACGGATTCTG", "AGGACGGATTCT"))
    }

    @Test
    fun validatesFirstStrandNotLonger() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "left and right strands must be of equal length."
        ) {
            Hamming.compute("AATG", "AAA")
        }
    }

    @Test
    fun validatesSecondStrandNotLonger() {
        assertThrows<java.lang.IllegalArgumentException>(
            message = "left and right strands must be of equal length."
        ) {
            Hamming.compute("ATA", "AGTG")
        }
    }
}

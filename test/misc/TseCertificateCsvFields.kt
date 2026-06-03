package misc

import leetcode.toRoman
import lib.StringArrayArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://www.bzst.de/DE/Unternehmen/Aussenpruefungen/DigitaleSchnittstelleFinV/digitaleschnittstellefinv_node.html
 *
 * The digital interface of the tax authorities for cash register systems (DSFinV-K)
 * defines a structure for data from electronic recording systems. The format is
 * a set of CSV files.
 *
 * One such file contains the certificate of the technical security system (TSS; or TSE in German).
 *
 * However, the certificate is split up into chunks of 1000 chars that are assigned to columns
 * TSE_ZERTIFIKAT_I, TSE_ZERTIFIKAT_II, TSE_ZERTIFIKAT_III, and so on depending how many parts the
 * certificate is split into.
 */
fun certCsvFields(cert: String, chunkSize: Int = 1000) =
    cert.chunkedSequence(chunkSize)
        .zip(romanNumerals())
        .map { (c, i) -> "TSE_ZERTIFIKAT_$i" to c }
        .toList()

fun romanNumerals() =
    (1..3999).asSequence().map { it.toRoman() }

/**
 * Unit tests
 */
class TseCertificateCsvTest {

    @Test
    fun `no cols when cert is empty`() =
        assertThat(
            certCsvFields("")
        ).isEmpty()

    @ParameterizedTest
    @CsvSource(
        "a; 1; [a]",
        "a; 1000; [a]",
        "abc; 1; [a, b, c]",
        "abc; 2; [ab, c]",
        "abcdefghijklmnopqrstuvwxyz; 3; [abc, def, ghi, jkl, mno, pqr, stu, vwx, yz]",
        delimiter = ';'
    )
    fun `cert is split up into n chunks with last chunk as partial`(
        cert: String,
        chunkSize: Int,
        @ConvertWith(StringArrayArg::class) expectedChunks: Array<String>
    ) {
        assertThat(
            certCsvFields(cert, chunkSize).map { (_, v) -> v }
        ).containsExactly(
            *expectedChunks
        )
    }

    @ParameterizedTest
    @CsvSource(
        "1; 1; [I]",
        "1; 1000; [I]",
        "1000; 1000; [I]",
        "1001; 1000; [I, II]",
        "2000; 1000; [I, II]",
        "2001; 1000; [I, II, III]",
        "3336; 1000; [I, II, III, IV]",
        "1000; 100; [I, II, III, IV, V, VI, VII, VIII, IX, X]",
        delimiter = ';'
    )
    fun `col names are enumerated with roman numerals`(
        certLength: Int,
        chunkSize: Int,
        @ConvertWith(StringArrayArg::class) expectedRomanNumerals: Array<String>
    ) {
        assertThat(
            certCsvFields("a".repeat(certLength), chunkSize).map { (c, _) -> c }
        ).containsExactlyElementsOf(
            expectedRomanNumerals.map { "TSE_ZERTIFIKAT_$it" }
        )
    }
}

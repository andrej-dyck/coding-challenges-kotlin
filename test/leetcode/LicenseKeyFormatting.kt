package leetcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/license-key-formatting/
 *
 * 482. License Key Formatting
 * [Easy]
 *
 * You are given a license key represented as a string S which consists only alphanumeric
 * character and dashes. The string is separated into N+1 groups by N dashes.
 *
 * Given a number K, we would want to reformat the strings such that each group contains
 * exactly K characters, except for the first group which could be shorter than K,
 * but still must contain at least one character. Furthermore, there must be a dash inserted
 * between two groups and all lowercase letters should be converted to uppercase.
 *
 * Given a non-empty string S and a number K, format the string according to the rules
 * described above.
 *
 * Note:
 * - The length of string S will not exceed 12,000, and K is a positive integer.
 * - String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
 * - String S is non-empty.
 */
fun formattedLicenseKey(licenceKey: String, groupSize: Int): String =
    licenceKey
        .remove("-")
        .reversed()
        .blocks("-", groupSize)
        .reversed()
        .uppercase()

fun String.remove(s: String) = replace(s, "")

fun String.blocks(separator: String, groupSize: Int) =
    chunked(groupSize).joinToString(separator)

/**
 * Unit tests
 */
class LicenseKeyFormattingTest {

    @ParameterizedTest
    @CsvSource(
        "5F3Z-2e-9-w, 4, 5F3Z-2E9W",
        "2-5g-3-J, 2, 2-5G-3J"
    )
    fun `formatted license key`(s: String, k: Int, expectedFormattedKey: String) {
        assertThat(
            formattedLicenseKey(s, k)
        ).isEqualTo(
            expectedFormattedKey
        )
    }
}

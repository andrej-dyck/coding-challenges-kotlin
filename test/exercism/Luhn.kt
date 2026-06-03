package exercism

import lib.isDigit
import lib.isEven
import lib.isMultipleOf
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/luhn
 *
 * Luhn
 * [Medium]
 *
 * Given a number determine whether or not it is valid per the Luhn formula.
 */
interface Luhn {

    val isValid: Boolean

    companion object {
        fun isValid(number: String) =
            StringValidation(numberAsString = number).isValid
    }

    class StringValidation(numberAsString: String) : Luhn {

        override val isValid by lazy {
            numberAsString.isValidLuhnString() &&
                Validation(numberAsString.toDigits()).isValid
        }

        private fun String.toDigits() =
            filterNot { it.isWhitespace() }.map(Character::getNumericValue)
    }

    class Validation(numberAsDigits: List<Int>) : Luhn {

        override val isValid by lazy {
            with(numberAsDigits) {
                size > 1
                    && onlyDigits()
                    && reversed()
                    .everySecondAsDigitSumOfDoubled()
                    .sum()
                    .isMultipleOf(10)
            }
        }

        private fun List<Int>.everySecondAsDigitSumOfDoubled() =
            mapIndexed { index, value ->
                if (index.isEven()) value
                else digitOfSumOfDoubled[value]
            }

        companion object {
            private val digitOfSumOfDoubled = arrayListOf(0, 2, 4, 6, 8, 1, 3, 5, 7, 9)
        }
    }
}

fun List<Int>.onlyDigits() = all { it.isDigit() }
fun String.isValidLuhnString() = all { it.isDigit() || it.isWhitespace() }

/**
 * Unit tests
 */
class LuhnTest {

    @Test
    fun singleDigitStringsCannotBeValid() {
        assertFalse(Luhn.isValid("1"))
    }

    @Test
    fun singleZeroIsInvalid() {
        assertFalse(Luhn.isValid("0"))
    }

    @Test
    fun simpleValidSINThatRemainsValidIfReversed() {
        assertTrue(Luhn.isValid("059"))
    }

    @Test
    fun simpleValidSINThatBecomesInvalidIfReversed() {
        assertTrue(Luhn.isValid("59"))
    }

    @Test
    fun validCanadianSIN() {
        assertTrue(Luhn.isValid("055 444 285"))
    }

    @Test
    fun invalidCanadianSIN() {
        assertFalse(Luhn.isValid("055 444 286"))
    }

    @Test
    fun invalidCreditCard() {
        assertFalse(Luhn.isValid("8273 1232 7352 0569"))
    }

    @Test
    fun validStringsWithNonDigitIncludedBecomeInvalid() {
        assertFalse(Luhn.isValid("055a 444 285"))
    }

    @Test
    fun validStringsWithPunctuationIncludedBecomeInvalid() {
        assertFalse(Luhn.isValid("055-444-285"))
    }

    @Test
    fun validStringsWithSymbolsIncludedBecomeInvalid() {
        assertFalse(Luhn.isValid("055£ 444$ 285"))
    }

    @Test
    fun singleZeroWithSpaceIsInvalid() {
        assertFalse(Luhn.isValid(" 0"))
    }

    @Test
    fun moreThanSingleZeroIsValid() {
        assertTrue(Luhn.isValid("0000 0"))
    }

    @Test
    fun inputDigit9IsCorrectlyConvertedToOutputDigit9() {
        assertTrue(Luhn.isValid("091"))
    }

    @Test
    fun stringsWithNonDigitsIsInvalid() {
        assertFalse(Luhn.isValid(":9"))
    }
}

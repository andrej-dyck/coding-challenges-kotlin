package lib

import java.math.BigDecimal
import java.math.RoundingMode

fun <T> T.require(predicate: (T) -> Boolean): T {
    require(predicate(this))
    return this
}

// Collection/Array Extensions
fun <T> Collection<T>.formattedString() = "[${joinToString(",")}]"

fun <T> Array<T>.formattedString() = toList().formattedString()

inline fun <T : Comparable<T>> Collection<T>.maxOr(otherwise: () -> T) =
    maxOrNull() ?: otherwise()

inline fun <T : Comparable<T>> Collection<T>.minOr(otherwise: () -> T) =
    minOrNull() ?: otherwise()

inline fun <reified T> Array<T>.headTails() = firstOrNull() to drop(1).toTypedArray()

// Sequence Extensions
fun <T> Sequence<T>.headTails() = firstOrNull() to drop(1)
fun <T> Sequence<T>.cycle() = generateSequence(this) { this }.flatten()

// Number Extensions
fun Int.isEven() = this % 2 == 0
fun Int.isOdd() = this % 2 == 1

fun Int.isDigit() = this in 0..9

infix fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0
infix fun Int.isNotDivisibleBy(divisor: Int) = !isDivisibleBy(divisor)
infix fun Int.isMultipleOf(number: Int) = this isDivisibleBy number

fun Double.round(decimals: Int) =
    BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()

// String Extensions
fun String.isANumber() = this.toIntOrNull() != null

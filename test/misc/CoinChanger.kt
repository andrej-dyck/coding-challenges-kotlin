package misc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * Coin Changer Kata
 *
 * For a given monetary amount in cents, return the minimal amount of change in coins.
 * Available coins with their denominations are:
 * - quarters: 25 cents
 * - dimes: 10 cents
 * - nickels: 5 cents
 * - pennies: 1 cent
 */
fun changeFor(cents: Cents) = Change(
    usCoins
        .remainders(cents)
        .zip(usCoins) { remCents, coin -> coin to remCents.value / coin.cents.value }
)

private val usCoins = listOf(Quarter, Dime, Nickel, Penny)

private fun List<Coin>.remainders(cents: Cents) =
    runningFold(cents) { rem, coin -> Cents(rem.value % coin.cents.value) }

data class Change(val coins: Map<Coin, Int>) {
    constructor(coinAmounts: Iterable<Pair<Coin, Int>>)
        : this(coinAmounts.filter { it.second != 0 }.toMap())
}

@JvmInline
value class Cents(val value: Int) {
    init {
        require(value >= 0)
    }
}

sealed class Coin(val cents: Cents)
object Quarter : Coin(Cents(25))
object Dime : Coin(Cents(10))
object Nickel : Coin(Cents(5))
object Penny : Coin(Cents(1))

/** Unit Tests */
class CoinChangerTest {

    @ParameterizedTest(name = "{index}: {0} cents results in change {1}")
    @CsvSource(
        "0; {}",
        "1; {penny: 1}",
        "4; {penny: 4}",
        "5; {nickel: 1}",
        "10; {dime: 1}",
        "17; {dime: 1, nickel: 1, penny: 2}",
        "20; {dime: 2}",
        "25; {quarter: 1}",
        "75; {quarter: 3}",
        "99; {quarter: 3, dime: 2, penny: 4}",
        "100; {quarter: 4}",
        "142; {quarter: 5, dime: 1, nickel: 1, penny: 2}",
        delimiter = ';'
    )
    fun `for given cents the coin changer returns the min amount of coins`(
        cents: Int,
        expectedChange: String
    ) {
        assertThat(
            changeFor(Cents(cents))
        ).isEqualTo(
            expectedChange.parseChangeObject()
        )
    }

    private fun String.parseChangeObject() = Change(
        keyValueMap { (coin, amount) -> coin.toCoin() to amount.toInt() }
    )

    private fun String.toCoin() = when (this) {
        "quarter" -> Quarter
        "dime" -> Dime
        "nickel" -> Nickel
        "penny" -> Penny
        else -> throw IllegalArgumentException("$this is not a coin")
    }
}

private fun <K, V> String.keyValueMap(transform: (Pair<String, String>) -> Pair<K, V>) =
    removeSurrounding("{", "}")
        .split(',')
        .filterNot { it.isEmpty() }
        .map { it.split(':') }
        .map { (key, value) -> key.trim() to value.trim() }
        .associate(transform)

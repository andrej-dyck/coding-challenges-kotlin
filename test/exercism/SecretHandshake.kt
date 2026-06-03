package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/secret-handshake
 *
 * Secret Handshake
 * [Easy]
 *
 * Given a decimal number, convert it to the appropriate sequence of events for a secret handshake.
 */
object HandshakeCalculator { // calculator ... really?! exercism is teaching some "good" OO :/

    fun calculateHandshake(number: Int) =
        BinarySecretHandshake(number).toList()
}

enum class Signal {
    WINK, DOUBLE_BLINK, CLOSE_YOUR_EYES, JUMP
}

// here's how to OO
interface SecretHandshake : Sequence<Signal>

class BinarySecretHandshake(number: Int) : SecretHandshake {

    private val signals by lazy {
        listOfNotNull(
            Signal.WINK.takeIf { number has 0b1 },
            Signal.DOUBLE_BLINK.takeIf { number has 0b10 },
            Signal.CLOSE_YOUR_EYES.takeIf { number has 0b100 },
            Signal.JUMP.takeIf { number has 0b1000 }
        ).reversedIf { number has 0b10000 }
    }

    override fun iterator() = signals.iterator()
}

private infix fun Int.has(binary: Int) = this and binary != 0

private fun <E> Collection<E>.reversedIf(predicate: () -> Boolean) =
    if (predicate()) this.reversed() else this

/**
 * Unit tests
 */
class HandshakeCalculatorTest {

    @Test
    fun testThatInput1YieldsAWink() {
        assertEquals(
            listOf(Signal.WINK),
            HandshakeCalculator.calculateHandshake(1)
        )
    }

    @Test
    fun testThatInput2YieldsADoubleBlink() {
        assertEquals(
            listOf(Signal.DOUBLE_BLINK),
            HandshakeCalculator.calculateHandshake(2)
        )
    }

    @Test
    fun testThatInput4YieldsACloseYourEyes() {
        assertEquals(
            listOf(Signal.CLOSE_YOUR_EYES),
            HandshakeCalculator.calculateHandshake(4)
        )
    }

    @Test
    fun testThatInput8YieldsAJump() {
        assertEquals(
            listOf(Signal.JUMP),
            HandshakeCalculator.calculateHandshake(8)
        )
    }

    @Test
    fun testAnInputThatYieldsTwoActions() {
        assertEquals(
            listOf(Signal.WINK, Signal.DOUBLE_BLINK),
            HandshakeCalculator.calculateHandshake(3)
        )
    }

    @Test
    fun testAnInputThatYieldsTwoReversedActions() {
        assertEquals(
            listOf(Signal.DOUBLE_BLINK, Signal.WINK),
            HandshakeCalculator.calculateHandshake(19)
        )
    }

    @Test
    fun testReversingASingleActionYieldsTheSameAction() {
        assertEquals(
            listOf(Signal.JUMP),
            HandshakeCalculator.calculateHandshake(24)
        )
    }

    @Test
    fun testReversingNoActionsYieldsNoActions() {
        assertEquals(
            emptyList<Signal>(),
            HandshakeCalculator.calculateHandshake(16)
        )
    }

    @Test
    fun testInputThatYieldsAllActions() {
        assertEquals(
            listOf(Signal.WINK, Signal.DOUBLE_BLINK, Signal.CLOSE_YOUR_EYES, Signal.JUMP),
            HandshakeCalculator.calculateHandshake(15)
        )
    }

    @Test
    fun testInputThatYieldsAllActionsReversed() {
        assertEquals(
            listOf(Signal.JUMP, Signal.CLOSE_YOUR_EYES, Signal.DOUBLE_BLINK, Signal.WINK),
            HandshakeCalculator.calculateHandshake(31)
        )
    }

    @Test
    fun testThatInput0YieldsNoActions() {
        assertEquals(
            emptyList<Signal>(),
            HandshakeCalculator.calculateHandshake(0)
        )
    }

    @Test
    fun testThatInputWithLower5BitsNotSetYieldsNoActions() {
        assertEquals(
            emptyList<Signal>(),
            HandshakeCalculator.calculateHandshake(32)
        )
    }
}

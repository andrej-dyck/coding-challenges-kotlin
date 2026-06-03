package exercism

import lib.round
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * https://exercism.io/tracks/kotlin/exercises/space-age
 *
 * Space Age
 * [Easy]
 *
 * Given an age in seconds, calculate how old someone is in terms of a given planet's solar years.
 */
class SpaceAge(private val inSeconds: Seconds) {

    fun onEarth() = EarthAge(inSeconds).inYearsAsRoundedDouble()
    fun onMercury() = MercuryAge(inSeconds).inYearsAsRoundedDouble()
    fun onVenus() = VenusAge(inSeconds).inYearsAsRoundedDouble()
    fun onMars() = MarsAge(inSeconds).inYearsAsRoundedDouble()
    fun onJupiter() = JupiterAge(inSeconds).inYearsAsRoundedDouble()
    fun onSaturn() = SaturnAge(inSeconds).inYearsAsRoundedDouble()
    fun onUranus() = UranusAge(inSeconds).inYearsAsRoundedDouble()
    fun onNeptune() = NeptuneAge(inSeconds).inYearsAsRoundedDouble()
}

typealias Seconds = Long

private fun AgeInYears.inYearsAsRoundedDouble() =
    inYears.asDouble.round(2)

/**
 * Age on Planets
 */
class EarthAge(inSeconds: Seconds) :
    AgeInYears by YearsAsAge(SecondsAsYears(inSeconds, secondsInAYear = 31_557_600L))

class MercuryAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 0.2408467)

class VenusAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 0.61519726)

class MarsAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 1.8808158)

class JupiterAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 11.862615)

class SaturnAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 29.447498)

class UranusAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 84.016846)

class NeptuneAge(inSeconds: Seconds) :
    AgeInYears by RelativeAgeTo(EarthAge(inSeconds), yearsRatio = 164.79132)

/**
 * Age and Years
 */
interface AgeInYears {

    val inYears: Years
}

class YearsAsAge(years: Years) : AgeInYears {

    override val inYears = years
}

class RelativeAgeTo(ageInYears: AgeInYears, yearsRatio: Double) :
    AgeInYears by YearsAsAge(RelativeYearsTo(ageInYears.inYears, yearsRatio))

interface Years {

    val asDouble: Double
}

class SecondsAsYears(seconds: Seconds, secondsInAYear: Seconds) : Years {

    override val asDouble by lazy {
        seconds.toDouble().div(secondsInAYear)
    }
}

class RelativeYearsTo(years: Years, yearsRatio: Double) : Years {

    override val asDouble by lazy {
        years.asDouble.div(yearsRatio)
    }
}

/**
 * Unit tests
 */
class SpaceAgeTest {

    @Test
    fun ageOnEarth() {
        val age = SpaceAge(1_000_000_000L)

        assertEquals(31.69, age.onEarth())
    }

    @Test
    fun ageOnMercury() {
        val age = SpaceAge(2_134_835_688)

        assertEquals(280.88, age.onMercury())
    }

    @Test
    fun ageOnVenus() {
        val age = SpaceAge(189_839_836)

        assertEquals(9.78, age.onVenus())
    }

    @Test
    fun ageOnMars() {
        val age = SpaceAge(2_329_871_239L)

        assertEquals(39.25, age.onMars())
    }

    @Test
    fun ageOnJupiter() {
        val age = SpaceAge(901_876_382)

        assertEquals(2.41, age.onJupiter())
    }

    @Test
    fun ageOnSaturn() {
        val age = SpaceAge(3_000_000_000L)

        assertEquals(3.23, age.onSaturn())
    }

    @Test
    fun ageOnUranus() {
        val age = SpaceAge(3_210_123_456L)

        assertEquals(1.21, age.onUranus())
    }

    @Test
    fun ageOnNeptune() {
        val age = SpaceAge(8_210_123_456L)

        assertEquals(1.58, age.onNeptune())
    }
}

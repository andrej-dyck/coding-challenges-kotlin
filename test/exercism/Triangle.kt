package exercism

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * https://exercism.io/tracks/kotlin/exercises/triangle
 *
 * Triangle
 * [Medium]
 *
 * Determine if a triangle is equilateral, isosceles, or scalene.
 */
class Triangle private constructor(sides: List<Double>) {

    constructor(sideA: Double, sideB: Double, sideC: Double) :
        this(listOf(sideA, sideB, sideC))

    constructor(sideA: Int, sideB: Int, sideC: Int) :
        this(sideA.toDouble(), sideB.toDouble(), sideC.toDouble())

    init {
        require(sides.all { it > 0 })
        require(sides.sorted().let { it[0] + it[1] >= it[2] })
    }

    val isEquilateral by lazy { distinctSides.size == 1 }
    val isIsosceles by lazy { distinctSides.size in 1..2 }
    val isScalene by lazy { distinctSides.size == 3 }

    private val distinctSides by lazy { sides.distinct() }
}

/**
 * Unit tests
 */
class TriangleTest {

    @Test
    fun equilateralIfAllSidesAreEqual() {
        assertTrue(Triangle(2, 2, 2).isEquilateral)
    }

    @Test
    fun notEquilateralIfAnySideIsUnequal() {
        assertFalse(Triangle(2, 3, 2).isEquilateral)
    }

    @Test
    fun notEquilateralIfNoSidesAreEqual() {
        assertFalse(Triangle(5, 4, 6).isEquilateral)
    }

    @Test
    fun allZeroSidesAreIllegalSoNotEquilateral() {
        assertThrows<java.lang.IllegalArgumentException> {
            assertFalse(Triangle(0, 0, 0).isEquilateral)
        }
    }

    @Test
    fun equilateralSidesMayBeFloatingPoint() {
        assertTrue(Triangle(0.5, 0.5, 0.5).isEquilateral)
    }

    @Test
    fun isoscelesIfLastTwoSidesAreEqual() {
        assertTrue(Triangle(3, 4, 4).isIsosceles)
    }

    @Test
    fun isoscelesIfFirstTwoSidesAreEqual() {
        assertTrue(Triangle(4, 4, 3).isIsosceles)
    }

    @Test
    fun isoscelesIfFirstAndLastSidesAreEqual() {
        assertTrue(Triangle(4, 3, 4).isIsosceles)
    }

    @Test
    fun equilateralIsAlsoIsosceles() {
        assertTrue(Triangle(4, 4, 4).isIsosceles)
    }

    @Test
    fun notIsoscelesIfNoSidesAreEqual() {
        assertFalse(Triangle(2, 3, 4).isIsosceles)
    }

    @Test
    fun sidesViolateTriangleInequalitySoNotIsosceles() {
        assertThrows<java.lang.IllegalArgumentException> {
            assertFalse(Triangle(1, 1, 3).isIsosceles)
        }
    }

    @Test
    fun isoscelesSidesMayBeFloatingPoint() {
        assertTrue(Triangle(0.5, 0.4, 0.5).isIsosceles)
    }

    @Test
    fun scaleneIfNoSidesAreEqual() {
        assertTrue(Triangle(5, 4, 6).isScalene)
    }

    @Test
    fun notScaleneIfAllSidesAreEqual() {
        assertFalse(Triangle(4, 4, 4).isScalene)
    }

    @Test
    fun notScaleneIfTwoSidesAreEqual() {
        assertFalse(Triangle(4, 4, 3).isScalene)
    }

    @Test
    fun sidesViolateTriangleInequalitySoNotScalene() {
        assertThrows<java.lang.IllegalArgumentException> {
            assertFalse(Triangle(7, 3, 2).isScalene)
        }
    }

    @Test
    fun scaleneSidesMayBeFloatingPoint() {
        assertTrue(Triangle(0.5, 0.4, 0.6).isScalene)
    }
}

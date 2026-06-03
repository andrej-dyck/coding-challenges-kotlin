package leetcode

import lib.IntArrayArg
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/
 *
 * 1700. Number of Students Unable to Eat Lunch
 * [Easy]
 *
 * The school cafeteria offers circular and square sandwiches at lunch break,
 * referred to by numbers 0 and 1 respectively.
 * All students stand in a queue. Each student either prefers square or circular sandwiches.
 *
 * The number of sandwiches in the cafeteria is equal to the number of students.
 * The sandwiches are placed in a stack. At each step:
 * - If the student at the front of the queue prefers the sandwich on the top of the stack,
 *   they will take it and leave the queue.
 * - Otherwise, they will leave it and go to the queue's end.
 *
 * This continues until none of the queue students want to take the top sandwich and are thus unable to eat.
 *
 * You are given two integer arrays students and sandwiches where sandwiches[i] is the type of the i-th sandwich
 * in the stack (i = 0 is the top of the stack) and students[j] is the preference of the j-th student
 * in the initial queue (j = 0 is the front of the queue). Return the number of students that are unable to eat.
 *
 * Constraints:
 * - 1 <= students.length, sandwiches.length <= 100
 * - students.length == sandwiches.length
 * - sandwiches[i] is 0 or 1.
 * - students[i] is 0 or 1.
 */
typealias SandwichType = Int

typealias StudentPreferences = Array<SandwichType>
typealias SandwichRequests = Map<SandwichType, Int>

typealias Sandwiches = Array<SandwichType>

fun hungryStudents(students: StudentPreferences, sandwiches: Sandwiches) =
    satisfyRequests(requests = students.groupingBy { it }.eachCount(), sandwiches)

tailrec fun satisfyRequests(requests: SandwichRequests, sandwiches: Sandwiches): Int =
    when {
        sandwiches.none() || requests.noneFor(sandwiches.first()) -> requests.sum()
        else -> satisfyRequests(
            requests.decrementFor(sandwiches.first()),
            sandwiches.withoutFirst()
        )
    }

fun SandwichRequests.noneFor(sandwich: SandwichType) = getOrDefault(sandwich, 0) == 0

fun SandwichRequests.sum() = values.sum()

fun SandwichRequests.decrementFor(sandwich: SandwichType) =
    mapValues { (k, v) -> if (k == sandwich) v - 1 else v }

fun Sandwiches.withoutFirst() = drop(1).toTypedArray()

/**
 * Unit Tests
 */
class NumberOfStudentsUnableToEatLunchTest {

    @ParameterizedTest
    @CsvSource(
        "[1]; [0]; 1",
        "[0]; [1]; 1",
        "[0]; [0]; 0",
        "[1]; [1]; 0",
        "[1,0]; [0,1]; 0",
        "[1,0]; [0,0]; 1",
        "[1,1]; [0,0]; 2",
        "[1,1]; [0,1]; 2",
        "[0,1]; [0,0]; 1",
        "[0,1]; [1,0]; 0",
        "[0,1]; [0,1]; 0",
        "[0,0]; [0,1]; 1",
        "[0,0]; [1,0]; 2",
        "[0,0]; [0,0]; 0",
        "[1,1,0,0]; [0,1,0,1]; 0",
        "[1,1,0,0]; [0,1,0]; 1",
        "[1,1,0]; [0,1,1,0]; 0",
        "[1,1,0]; [0,1,0,1]; 1",
        "[1,1,1,0,0,1]; [1,0,0,0,1,1]; 3",
        delimiter = ';'
    )
    fun `the number of students that are unable to eat`(
        @ConvertWith(IntArrayArg::class) studentPreferences: StudentPreferences,
        @ConvertWith(IntArrayArg::class) sandwiches: Sandwiches,
        expectedHungryStudents: Int
    ) {
        Assertions.assertThat(
            hungryStudents(studentPreferences, sandwiches)
        ).isEqualTo(
            expectedHungryStudents
        )
    }
}

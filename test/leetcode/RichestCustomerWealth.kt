package leetcode

import lib.IntMatrixArg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

/**
 * https://leetcode.com/problems/richest-customer-wealth/
 *
 * 1672. Richest Customer Wealth
 * [Easy]
 *
 * You are given an m x n integer grid accounts where accounts[i][j] is
 * the amount of money the i-th customer has in the j-th bank.
 *
 * Return the wealth that the richest customer has.
 *
 * A customer's wealth is the amount of money they have in all their bank accounts.
 * The richest customer is the customer that has the maximum wealth.
 *
 * Constraints:
 * - m == accounts.length
 * - n == accounts[i].length
 * - 1 <= m, n <= 50
 * - 1 <= accounts[i][j] <= 100
 */
fun maximumWealth(accounts: Array<Array<Int>>) =
    accounts.maxOfOrNull { it.sum() }

/**
 * Unit tests
 */
class RichestCustomerWealthTest {

    @ParameterizedTest
    @CsvSource(
        "[[1]]; 1",
        "[[1,2,3],[3,2,1]]; 6",
        "[[1,5],[7,3],[3,5]]; 10",
        "[[2,8,7],[7,1,3],[1,9,5]]; 17",
        delimiter = ';'
    )
    fun `max wealth of the richest customer is the sum of their account values`(
        @ConvertWith(IntMatrixArg::class) accounts: Array<Array<Int>>,
        expectedWealth: Int
    ) {
        assertThat(
            maximumWealth(accounts)
        ).isEqualTo(
            expectedWealth
        )
    }
}

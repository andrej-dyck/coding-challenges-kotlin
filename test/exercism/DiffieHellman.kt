package exercism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.Random

object DiffieHellman {

    fun publicKey(p: BigInteger, g: BigInteger, privateKey: BigInteger): BigInteger =
        g.modPow(privateKey, p)

    fun secret(prime: BigInteger, publicKey: BigInteger, privateKey: BigInteger): BigInteger =
        publicKey.modPow(privateKey, prime)

    /**
     * The private key itself should be a large prime itself. To this end,
     * BigInteger's probablePrime() method should be used.
     *
     * However, with too few bits (e.g., 3 bits for the prime 5) the method
     * might never return a result in range 1 until prime. Thus, I divided
     * this method into two parts depending on how large the prime is.
     */
    fun privateKey(prime: BigInteger) =
        if (prime.bitLength() <= 8) randomSmallPrime(prime) else randomLargePrime(prime)

    private tailrec fun randomSmallPrime(prime: BigInteger): BigInteger {
        val privateKey = (2 until prime.toShort()).random().toBigInteger()
        return when {
            privateKey.isProbablePrime(100) -> privateKey
            else -> randomSmallPrime(prime)
        }
    }

    private tailrec fun randomLargePrime(prime: BigInteger): BigInteger {
        val privateKey = BigInteger.probablePrime(prime.bitLength(), Random())
        return when {
            BigInteger.ONE < privateKey && privateKey < prime -> privateKey
            else -> randomLargePrime(prime)
        }
    }
}

/**
 * Unit Tests
 */
class DiffieHellmanTest {

    @Test
    fun `private key is in range from 1 to prime`() {
        val prime = 23.toBigInteger()
        (0..9).map { DiffieHellman.privateKey(prime) }.forEach {
            assertTrue(it >= BigInteger.ONE)
            assertTrue(it < prime)
        }
    }

    /**
     * Due to the nature of randomness, there is always a chance that this test fails.
     * Be sure to check the actual generated values.
     */
    @Test
    fun `private key is random`() {
        val prime = 7919.toBigInteger()
        val privateKeyA = DiffieHellman.privateKey(prime)
        val privateKeyB = DiffieHellman.privateKey(prime)

        assertNotEquals(privateKeyA, privateKeyB)
    }

    @Test
    fun `large private keys are primes that have the same bit length`() {
        val prime = 2_147_483_647.toBigInteger()
        val privateKey = DiffieHellman.privateKey(prime)

        assertTrue(privateKey.isProbablePrime(100))
        assertEquals(prime.bitLength(), privateKey.bitLength())
    }

    @Test
    fun `calculate public key using private key`() {
        val primeA = 23.toBigInteger()
        val primeB = 5.toBigInteger()
        val privateKey = 6.toBigInteger()
        val expected = 8.toBigInteger()

        assertEquals(expected, DiffieHellman.publicKey(primeA, primeB, privateKey))
    }

    @Test
    fun `calculate secret using other party's public key`() {
        val prime = 23.toBigInteger()
        val publicKey = 19.toBigInteger()
        val privateKey = 6.toBigInteger()
        val expected = 2.toBigInteger()

        assertEquals(expected, DiffieHellman.secret(prime, publicKey, privateKey))
    }

    @Test
    fun `key exchange`() {
        val primeA = 23.toBigInteger()
        val primeB = 5.toBigInteger()

        val alicePrivateKey = DiffieHellman.privateKey(primeA)
        val bobPrivateKey = DiffieHellman.privateKey(primeB)

        val alicePublicKey = DiffieHellman.publicKey(primeA, primeB, alicePrivateKey)
        val bobPublicKey = DiffieHellman.publicKey(primeA, primeB, bobPrivateKey)

        val secretA = DiffieHellman.secret(primeA, bobPublicKey, alicePrivateKey)
        val secretB = DiffieHellman.secret(primeA, alicePublicKey, bobPrivateKey)

        assertEquals(secretA, secretB)
    }
}

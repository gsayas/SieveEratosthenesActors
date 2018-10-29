package edu.coursera.concurrent;

import junit.framework.TestCase;

public class SieveActorTest extends TestCase {

    SieveActor sieve;

    public void setUp() throws Exception {
        super.setUp();
        sieve = new SieveActor();
    }

    public void testLimitOne() {

        int primes = sieve.countPrimes(1);
        assertEquals(0, primes);
    }

    public void testLimitTwo() {

        int primes = sieve.countPrimes(2);
        assertEquals(1, primes);
    }

    public void testLimitThree() {

        int primes = sieve.countPrimes(3);
        assertEquals(2, primes);
    }

    public void testLimitFour() {

        int primes = sieve.countPrimes(4);
        assertEquals(2, primes);
    }

    public void testLimitFive() {

        int primes = sieve.countPrimes(5);
        assertEquals(3, primes);
    }

    public void testLimitSix() {

        int primes = sieve.countPrimes(6);
        assertEquals(3, primes);
    }

    public void testLimitSeven() {

        int primes = sieve.countPrimes(7);
        assertEquals(4, primes);
    }

    public void testLimit500_000() {

        int primes = sieve.countPrimes(500_000);
        assertEquals(41538, primes);
    }
}
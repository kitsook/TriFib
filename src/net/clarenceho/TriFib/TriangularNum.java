package net.clarenceho.TriFib;

import java.util.stream.Stream;
import java.math.BigInteger;

public class TriangularNum {
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger EIGHT = new BigInteger("8");

    /**
     * Stream of triangular numbers in BigInteger
     * @return  stream of triangular numbers
     */
    public static Stream<BigInteger> stream() {
        return Stream
            .iterate(
                BigInteger.ONE,
                i -> i.add(BigInteger.ONE))
            .map(i -> i.multiply(i.add(BigInteger.ONE)).divide(TWO));
    }
    
    /**
     * Test if a given number is a triangular number.
     * A integer x is triangular iff 8x + 1 is a perfect square.
     * 
     * @param x a BigInteger to be tested
     * @return  true if x is a triangular number. false otherwise
     */
    public static boolean isTriangular(BigInteger x) {
        BigInteger test = x.multiply(EIGHT).add(BigInteger.ONE);
        BigInteger testSqrt = sqrt(test);
        return test.equals(testSqrt.multiply(testSqrt));
    }
    
    /*
     * Calculate square root of an BigInteger.
     * Copied from https://stackoverflow.com/questions/2685524/check-if-biginteger-is-not-a-perfect-square
     */
    private static BigInteger sqrt(BigInteger n) {
        if (n.signum() >= 0) {
            final int bitLength = n.bitLength();
            BigInteger root = BigInteger.ONE.shiftLeft(bitLength / 2);

            while (!isSqrt(n, root)) {
                root = root.add(n.divide(root)).divide(TWO);
            }
            return root;
        } else {
            throw new ArithmeticException("square root of negative number");
        }
    }

    private static boolean isSqrt(BigInteger n, BigInteger root) {
        final BigInteger lowerBound = root.pow(2);
        final BigInteger upperBound = root.add(BigInteger.ONE).pow(2);
        return lowerBound.compareTo(n) <= 0
            && n.compareTo(upperBound) < 0;
    }
}

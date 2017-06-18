package net.clarenceho.TriFib;

import java.util.stream.Stream;
import java.math.BigInteger;

public class FibonacciNum {

    /**
     * Stream of Fibonacci number in BigInteger
     * @return  stream of Fibonacci number
     */
    public static Stream<BigInteger> stream() {
        return Stream
            .iterate(
                new BigInteger[] { BigInteger.ONE, BigInteger.ONE },
                p -> new BigInteger[] { p[1], p[0].add(p[1]) })
            .map(p -> p[0]);
    }

}

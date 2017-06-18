package net.clarenceho.TriFib.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.hamcrest.CoreMatchers.*;

import net.clarenceho.TriFib.FibonacciNum;
import net.clarenceho.TriFib.TriangularNum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test {

    // how many Fibonacci number to test?
    private static final int TEST_LIMIT = 70;
    
    // Expect numbers that are both triangular and Fibonacci
    private static final BigInteger[] EXPECTED = {
        BigInteger.ONE, new BigInteger("3"), new BigInteger("21"), new BigInteger("55") };
    
    
    @org.junit.Test
    public void test01CompareFirst30Tri() {
        Object[] tri = TriangularNum.stream().limit(30).map(i -> i.intValue()).toArray();
        int[] expected = {
                1, 3, 6, 10, 15, 21, 28, 36, 45, 55,
                66, 78, 91, 105, 120, 136, 153, 171, 190, 210,
                231, 253, 276, 300, 325, 351, 378, 406, 435, 465 };
        
        assertThat(tri, is(expected));
    }

    @org.junit.Test
    public void test02CompareFirst25Fib() {
        Object[] fib = FibonacciNum.stream().limit(25).map(i -> i.intValue()).toArray();
        int[] expected = {
                1, 1, 2, 3, 5, 8, 13, 21, 34, 55,
                89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765,
                10946, 17711, 28657, 46368, 75025 };
        
        assertThat(fib, is(expected));
    }

    @org.junit.Test
    public void test99SlowVerify() {
        
        Iterator<BigInteger> fib = FibonacciNum.stream().limit(TEST_LIMIT).iterator();
        Iterator<BigInteger> tri = TriangularNum.stream().iterator();
        
        BigInteger t = tri.next();
        
        List<BigInteger> result = new ArrayList<BigInteger>();
        
        while (fib.hasNext()) {
            BigInteger f = fib.next();
            while (t.compareTo(f) <= 0) {
                if (t.compareTo(f) == 0) {
                    result.add(t);
                }
                t = tri.next();
            }
        }
        
        assertThat(result.toArray(), is(EXPECTED));
    }

    @org.junit.Test
    public void test99FastVerify() {
        
        List<BigInteger> result = FibonacciNum
            .stream()
            .limit(TEST_LIMIT)
            .parallel()
            .filter(f -> TriangularNum.isTriangular(f))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        
        assertThat(result.toArray(), is(EXPECTED));
    }

}

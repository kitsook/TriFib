# TriFib

Read this [interesting blog](https://nedbatchelder.com/blog/201706/triangular_fibonacci_numbers.html) post on testing triangular Fibonacci numbers.  And I decided to implement a similar test in Java.

First, a Java Stream to produce [triangular numbers](https://en.wikipedia.org/wiki/Triangular_number).
```java
    public static Stream<BigInteger> stream() {
        return Stream
            .iterate(
                BigInteger.ONE,
                i -> i.add(BigInteger.ONE))
            .map(i -> i.multiply(i.add(BigInteger.ONE)).divide(TWO));
    }
```
And a Stream for Fibonacci sequence.
```java
    public static Stream<BigInteger> stream() {
        return Stream
            .iterate(
                new BigInteger[] { BigInteger.ONE, BigInteger.ONE },
                p -> new BigInteger[] { p[1], p[0].add(p[1]) })
            .map(p -> p[0]);
    }
```
Now, a simple and naive way to test for a triangular Fibonacci number is to loop the Fibonacci sequence while testing for the number's existence in the stream of triangular numbers.
```java
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
```
But since the Fibonacci sequence grows so quickly, it is a waste of CPU time to generate all those triangular numbers.  A quicker way is to ditch the triangular number stream and implement a test function for triangular number.  We then use that function to filter the Fibonacci stream.
```java
        List<BigInteger> result = FibonacciNum
            .stream()
            .limit(TEST_LIMIT)
            .parallel()
            .filter(f -> TriangularNum.isTriangular(f))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
```
Testing the first 70 Fibonacci numbers, the time diff between the two approaches is huge (24ms vs 4s).

And with the fast approach, on an i5 4210 machine, testing the first 50,000 Fibonacci numbers will take 93s.

